package com.spingdatajpa.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.spingdatajpa.springboot.entity.Product;
import com.spingdatajpa.springboot.entity.State;
import com.spingdatajpa.springboot.error.exception.WrappedException;
import com.spingdatajpa.springboot.service.impl.ProductService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.spingdatajpa.springboot.error.ErrorConstants.NOT_FOUND;

@RestController
@RequestMapping("product/")
public class ProductController {
    private final ProductService productService;

    private final RabbitTemplate template;

    private final Queue queue;

    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public ProductController(ProductService productService, RabbitTemplate template, Queue queue) {
        this.productService = productService;
        this.template = template;
        this.queue = queue;
    }

    @GetMapping("product/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable("id") int id){
        Product p = productService.getProduct(id);
        if(p==null){
            throw new WrappedException(NOT_FOUND);
        }
        return p;
    }
    @DeleteMapping("product/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") int id){
        ObjectNode data = OBJECT_MAPPER.createObjectNode();
        Product p = productService.getProduct(id);
        if(p==null){
            throw new WrappedException(NOT_FOUND);
        }
        productService.deleteProduct(id);
        data.put("deletedProductId", id);
        this.template.convertAndSend(queue.getName(), data);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    @PutMapping("/product/update/{id}")
    public Product updateProduct(@PathVariable int id, @RequestBody Product product){
        Product p = productService.getProduct(id);
        if(p==null){
            throw new WrappedException(NOT_FOUND);
        }
        return productService.updateProduct(id, product);
    }
    @PutMapping("{id}/state")
    public Product addState(@PathVariable int id, @RequestBody State state){
        Product p = productService.getProduct(id);
        if(p==null){
            throw new WrappedException(NOT_FOUND);
        }
        return productService.addState(id, state);
    }
    @GetMapping("onSale")
    public ResponseEntity<List<Product>> getProductsOnSale(){
        return new ResponseEntity<>(productService.getProuctsOnSale(), HttpStatus.OK);
    }
    @GetMapping("category/{name}")
    public ResponseEntity<List<Product>> getProductsByCategory(String name){
        return new ResponseEntity<>(productService.getAllProductsByCategory(name), HttpStatus.OK);
    }
    @GetMapping("name/{name}")
    public ResponseEntity<List<Product>> getProductsByName(String name){
        return new ResponseEntity<>(productService.getAllProductsByName(name), HttpStatus.OK);
    }
    @PostMapping("create")
    public Product createProduct( @RequestBody Product product){
        return productService.createProduct(product);
    }




}
