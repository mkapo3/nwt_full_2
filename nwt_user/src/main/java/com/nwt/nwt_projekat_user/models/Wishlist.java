package com.nwt.nwt_projekat_user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="wishlists")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "wishlist")
    private CustomUser customUser;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wishlist")
    private Set<WishlistProduct> wishlistProducts;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }

    public Set<WishlistProduct> getWishlistProducts() {
        return wishlistProducts;
    }

    public void setWishlistProducts(Set<WishlistProduct> wishlistProducts) {
        this.wishlistProducts = wishlistProducts;
    }
}
