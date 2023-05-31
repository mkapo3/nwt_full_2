package com.nwt.nwt_projekat_user.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
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

    public JsonNode getRequest(String uri){
        productHost = discoveryClient.getInstances("PRODUCTSERVICE").get(0).getUri().toString();
        ResponseEntity<String> response = this.restTemplate.getForEntity(productHost + uri, String.class);
        JsonNode node = null;
        try {
            node = MAPPER.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return node;
    }

}
