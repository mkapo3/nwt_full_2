package com.spingdatajpa.springboot.controller;

import com.spingdatajpa.springboot.Repository.CategoryRepository;
import com.spingdatajpa.springboot.Repository.ProductRepository;
import com.spingdatajpa.springboot.Repository.StateRepository;
import com.spingdatajpa.springboot.entity.Category;
import com.spingdatajpa.springboot.entity.Product;
import com.spingdatajpa.springboot.entity.State;
import com.spingdatajpa.springboot.service.impl.CategoryService;
import com.spingdatajpa.springboot.service.impl.ProductService;
import com.spingdatajpa.springboot.service.impl.StateService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductService service;

    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private CategoryRepository categoryRepository;
    @MockBean
    private StateRepository stateRepository;
    @MockBean
    private CategoryService categoryService;
    @MockBean
    private StateService stateService;
    @Test
    public void createProduct() throws Exception {



        Product mockProduct = mock(Product.class);
        mockProduct.setName("string");
        // Postavljanje željenih vrijednosti za mockProduct
        when(mockProduct.getId()).thenReturn(1);
        when(mockProduct.getName()).thenReturn("string");
        when(mockProduct.getColor()).thenReturn("string");
        when(mockProduct.getSastav()).thenReturn("string");



        when(mockProduct.getState()).thenReturn(Collections.singletonList(null));

        Category category = new Category();
        category.setId(1);
        category.setNaziv("string");

        when(mockProduct.getCategory()).thenReturn(category);
        when(mockProduct.getSpol()).thenReturn("string");
        when(mockProduct.getRezrevacijaId()).thenReturn(1);

        // Slanje POST zahtjeva na "/create" endpoint
        mvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":0,\"name\":\"string\",\"color\":\"string\",\"sastav\":\"string\",\"state\":[],\"category\":{\"id\":0,\"naziv\":\"string\"},\"spol\":\"string\",\"rezrevacijaId\":0}"))
                .andExpect(status().isOk());

        // Provjera pozivanja metode createProduct sa ispravnim argumentom
        verify(service).createProduct(any(Product.class));




    }
    @Test
    public void getProduct() throws Exception {
        Product mockProduct = new Product();
        mockProduct.setId(1);
        when(service.getProduct(1)).thenReturn(mockProduct);

        // Send GET request to the endpoint
        MockHttpServletRequestBuilder requestBuilder = get("/product/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }
    }
