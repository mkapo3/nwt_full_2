package com.example.nwt_api_gateway.filter;

import com.example.nwt_api_gateway.error.exception.WrappedException;
import com.example.nwt_api_gateway.model.Authority;
import com.example.nwt_api_gateway.req_res.ValidateTokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static com.example.nwt_api_gateway.error.ErrorConstants.INVALID_LOGIN_DETAILS;
import static com.example.nwt_api_gateway.error.ErrorConstants.TOKEN_NOT_FOUND;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator routeValidator;

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;


    public AuthenticationFilter() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new WrappedException(TOKEN_NOT_FOUND);
                }
                String authheaders = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if(authheaders != null && authheaders.startsWith("Bearer")){
                    authheaders = authheaders.substring(7);
                }
                ObjectNode data = OBJECT_MAPPER.createObjectNode();
                data.put("token", authheaders);

                try{
                    String host = discoveryClient.getInstances("NWTPROJEKATUSER").get(0).getUri().toString();
                    HttpEntity<ObjectNode> entity = new HttpEntity<>(data);
                    ResponseEntity<String> response = restTemplate.exchange(host + "/user/auth/validate", HttpMethod.POST, entity, String.class);
                    if(!response.getStatusCode().is2xxSuccessful()){
                        throw new WrappedException(INVALID_LOGIN_DETAILS);
                    }
                    ValidateTokenResponse validateTokenResponse = OBJECT_MAPPER.readValue(response.getBody(), ValidateTokenResponse.class);
                    exchange.getRequest().mutate().header("username", validateTokenResponse.getEmail());
                    exchange.getRequest().mutate().header("authorities", validateTokenResponse.getAuthorities().stream().map(Authority::getAuthority).reduce("", (a, b) -> a + "," + b));
                    exchange.getRequest().mutate().header("Authorization", "Bearer " + authheaders);
                }
                catch (Exception e){
                        throw new RuntimeException(e);
                }

            }
            return chain.filter(exchange);
        }));
    }

    public static class Config{

    }

}
