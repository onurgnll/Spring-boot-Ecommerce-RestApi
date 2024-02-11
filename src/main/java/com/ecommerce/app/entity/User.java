package com.ecommerce.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;


    private String name;


    private String email;
    private String password;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Product> products;



    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE , CascadeType.PERSIST})
    @JoinTable(name = "favorites" , joinColumns = @JoinColumn(name = "user_id") ,inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> favorites;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<CartItem> cartItems;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orders;


    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;



    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Shipment> shipments;



    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

}
