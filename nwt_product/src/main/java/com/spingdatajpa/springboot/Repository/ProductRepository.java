package com.spingdatajpa.springboot.Repository;

import com.spingdatajpa.springboot.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT u from Product u, State s where s.procenat>0 and u.id=s.proizvod.id ")
    List<Product> findProductsOnSale();
    List<Product>findProductByCategory_Naziv(String name);

    List<Product> findAllByName(String name);

    @Query("select p from Product p where p.name like %:search%")
    List<Product> findAllSearch(String search);

    Product findAllById(int id);
}
