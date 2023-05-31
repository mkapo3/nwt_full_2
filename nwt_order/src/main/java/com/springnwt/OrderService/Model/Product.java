package com.springnwt.OrderService.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Name can't be blank")
    private String name;
    @ManyToOne
    private State state;
    /*@OneToMany(mappedBy="product")
    private Set<ProductForCart> productsForCart;*/

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_for_cart_id",referencedColumnName = "id")
    private ProductForCart productForCart;
    public Product() {
    }

    public Product(String name, State state, ProductForCart productForCart) {
        this.name = name;
        this.state = state;
        this.productForCart = productForCart;
    }

    public ProductForCart getProductForCart() {
        return productForCart;
    }

    public void setProductForCart(ProductForCart productForCart) {
        this.productForCart = productForCart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }


}
