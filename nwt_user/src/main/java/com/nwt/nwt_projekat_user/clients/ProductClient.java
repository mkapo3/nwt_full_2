package com.nwt.nwt_projekat_user.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class ProductClient {

    private String productHost;

    public static final ObjectMapper MAPPER = new ObjectMapper();

    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;

    public ProductClient(DiscoveryClient discoveryClient){
        this.discoveryClient = discoveryClient;
        this.restTemplate = new RestTemplate();
    }

    public JsonNode getRequest(String uri, MultiValueMap<String, String> headers){
        productHost = discoveryClient.getInstances("PRODUCTSERVICE").get(0).getUri().toString();
        HttpEntity<ObjectNode> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response = this.restTemplate.exchange(productHost + uri, HttpMethod.GET, entity, String.class);
        JsonNode node = null;
        try {
            node = MAPPER.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return node;
    }

}
