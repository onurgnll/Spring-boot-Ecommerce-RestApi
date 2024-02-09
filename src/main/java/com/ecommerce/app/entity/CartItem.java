package com.ecommerce.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;


@Entity
@Data
@Table(name = "cart")
public class CartItem {

    @JsonIgnore
    @EmbeddedId
    private CartItemId cartItemId;

    public CartItemId getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(CartItemId cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH ,CascadeType.MERGE, CascadeType.PERSIST})
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH ,CascadeType.MERGE, CascadeType.PERSIST})
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;


    private int quantity;

}
