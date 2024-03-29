package com.example.nwt_api_gateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    public static final List<String> openApiEndpoints = List.of(
            "/user/auth",
            "/webjars/",
            "/v2/",
            "/actuator/",
            "/swagger-resources/",
            "/v3/api-docs/",
            "/swagger-ui/",
            "/swagger-ui.html"
    );

    public Predicate<ServerHttpRequest> isSecured = serverHttpRequest ->
        openApiEndpoints.stream().noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri) || uri.contains(serverHttpRequest.getURI().getPath()));



}
