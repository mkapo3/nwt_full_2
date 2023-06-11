package com.spingdatajpa.springboot.service.impl;

import com.spingdatajpa.springboot.Repository.ProductRepository;
import com.spingdatajpa.springboot.entity.Product;
import com.spingdatajpa.springboot.entity.State;
import com.spingdatajpa.springboot.service.IProductService;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
       productRepository.deleteById(id);
    }

    @Override
    public Product getProduct(int id) {

        return  productRepository.findAllById(id);
    }
    @Override
    public List<Product> getProuctsOnSale(){
        return productRepository.findProductsOnSale();
    }

    @Override
    public List<Product> getAllProductsByCategory(String name) {
        return productRepository.findProductByCategory_Naziv(name);
    }

    @Override
    public List<Product> getAllProductsByName(String name) {
        return productRepository.findAllByName(name);
    }

    @Override
    public Product addState(int id, State state) {
        Product p = productRepository.findAllById(id);
        p.getState().add(state);
        return productRepository.save(p);

    }

    @Override
    public List<Product> getAllProducts(String search) {
        return  productRepository.findAllSearch(search);
    }

    @Override
    public Product updateProduct(int id, Product product) {
        Product p = productRepository.findAllById(id);
        p.setCategory(product.getCategory());
        p.setColor(product.getColor());
        p.setSastav(product.getSastav());
        p.setName(product.getName());
        p.setSpol(product.getSpol());
        p.setState(product.getState());
        return productRepository.save(p);
    }
}
