package com.springnwt.OrderService.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Set;

@Entity
public class State {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @Min(0)
    private Double price;
    @Min(0)
    @Max(1)
    private Double percentage;
    @OneToMany(mappedBy = "state")
    Set<Product> products;

    public State() {
    }

    public State(Double price, Double percentage, Set<Product> products) {
        this.price = price;
        this.percentage = percentage;
        this.products = products;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
