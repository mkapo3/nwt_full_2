package com.nwt.nwt_projekat_user.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.Set;

@Entity
@Table(name="custom_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
public class CustomUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Pattern(regexp = "^[A-Z][A-Za-z]+ [A-Z][A-Za-z]+", message = "Name must contain firstname and lastname with space between and first letter as upper case")
    @NotNull
    private String name;

    @NotNull
    private String address;

    @Pattern(regexp = "^\\+[0-9].{6,}", message = "Phone number must be in +1111111 format")
    @NotNull
    private String phoneNumber;

    @Email
    @Pattern(regexp = "^.*@.*\\..*", message = "Email must be in xxxx@xxxx.xx format")
    @NotNull
    private String email;

    @Pattern(regexp = "^(?=.*[^A-Za-z0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}", message = "Password must contain one upper case letter, lower case letter, special character, number and must be 8 characters long")
    @NotNull
    private String password;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Reservation> reservations;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wishlist_id", referencedColumnName = "id")
    private Wishlist wishlist;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "custom_user_roles",
            joinColumns = @JoinColumn(name = "custom_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
