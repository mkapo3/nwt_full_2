package com.springnwt.OrderService.Model;

import jakarta.persistence.*;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private boolean deleted;

    @OneToOne(mappedBy = "cart")
    private Orders order;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_for_cart_id",referencedColumnName = "id")
    private ProductForCart productForCart;

    public Cart() {
    }

    public Cart(boolean deleted, Orders order, ProductForCart productForCart) {
        this.deleted = deleted;
        this.order = order;
        this.productForCart = productForCart;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public ProductForCart getProductForCart() {
        return productForCart;
    }

    public void setProductForCart(ProductForCart productForCart) {
        this.productForCart = productForCart;
    }
}
