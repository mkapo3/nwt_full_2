package com.nwt.nwt_projekat_user.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwt.nwt_projekat_user.clients.ProductClient;
import com.nwt.nwt_projekat_user.external_responses.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;

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

    public CategoryResponse getCategory(Long id){
        JsonNode response = productClient.getRequest("/api/category/" + id.toString());
        CategoryResponse categoryResponse = null;
        try {
            categoryResponse = MAPPER.treeToValue(response, CategoryResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return categoryResponse;
    }


}
