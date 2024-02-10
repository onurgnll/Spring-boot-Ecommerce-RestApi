package com.ecommerce.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;


    private String name;

    private Double price;

    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;


    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE , CascadeType.PERSIST})
    @JoinTable(name = "favorites" , joinColumns = @JoinColumn(name = "product_id") ,inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> favoritedUsers;


    @JsonIgnore
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<CartItem> cartItems;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE ,CascadeType.PERSIST ,CascadeType.REFRESH})
    @JoinTable(name = "category_product" , joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderItem> orderItems;
}
