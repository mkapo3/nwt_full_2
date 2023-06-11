package com.nwt.nwt_projekat_user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="carts")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(columnDefinition = "boolean default false")
    private Boolean deleted = false;

    @JsonIgnore
    @OneToOne(mappedBy = "cart")
    private CustomUser customUser;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    private Set<CartProduct> cartProducts;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cart")
    private Order order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

    public Set<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(Set<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
