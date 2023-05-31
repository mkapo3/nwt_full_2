package com.springnwt.OrderService.Model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class ProductForCart {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private Integer quantity;
    @OneToMany(mappedBy="productForCart")
    private Set<Cart> carts;
    /*@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id",referencedColumnName = "id")
    private Product product;*/

    @OneToMany(mappedBy="productForCart")
    private Set<Product> products;

    public ProductForCart() {
    }


    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Set<Cart> getCarts() {
        return carts;
    }

    public void setCarts(Set<Cart> carts) {
        this.carts = carts;
    }

    public ProductForCart(Integer quantity, Set<Cart> carts, Set<Product> products) {
        this.quantity = quantity;
        this.carts = carts;
        this.products = products;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
