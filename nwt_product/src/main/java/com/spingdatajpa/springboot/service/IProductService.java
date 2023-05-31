package com.spingdatajpa.springboot.service;

import com.spingdatajpa.springboot.entity.Product;
import com.spingdatajpa.springboot.entity.State;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    public Product createProduct(Product product);
    public void deleteProduct(int id);
    public Product getProduct(int id);
    public List<Product> getAllProducts();
    public Product updateProduct(int id, Product product);
    public List<Product> getProuctsOnSale();
    public List<Product> getAllProductsByCategory(String name);
    public List<Product> getAllProductsByName(String name);

    Product addState(int id, State state);
}
