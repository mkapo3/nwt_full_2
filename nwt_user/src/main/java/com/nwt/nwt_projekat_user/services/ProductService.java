package com.nwt.nwt_projekat_user.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwt.nwt_projekat_user.clients.ProductClient;
import com.nwt.nwt_projekat_user.external_responses.CategoryResponse;
import com.nwt.nwt_projekat_user.external_responses.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;

@Service
@EnableDiscoveryClient
public class ProductService {

    private ProductClient productClient;

    public static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    DiscoveryClient discoveryClient;


    @PostConstruct
    public void init(){
        this.productClient = new ProductClient(discoveryClient);
    }

    public CategoryResponse getCategory(Long id, String authHeader){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", authHeader);
        JsonNode response = productClient.getRequest("/product/category/" + id.toString(), headers);
        CategoryResponse categoryResponse = null;
        try {
            categoryResponse = MAPPER.treeToValue(response, CategoryResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return categoryResponse;
    }


    public ProductResponse getProduct(Long id, String authHeader){
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Authorization", authHeader);
        JsonNode response = productClient.getRequest("/product/product/" + id.toString(), headers);
        ProductResponse productResponse = null;
        try {
            productResponse = MAPPER.treeToValue(response, ProductResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return productResponse;
    }


}
