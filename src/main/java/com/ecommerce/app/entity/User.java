package com.ecommerce.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;


    private String name;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;
    private String address;

    @JsonIgnore
    private boolean accountNonExpired = true;
    @JsonIgnore
    private boolean accountNonLocked = true;
    @JsonIgnore
    private boolean isEnabled = true;
    @JsonIgnore
    private boolean credentialsNonExpired = true;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role" , joinColumns = @JoinColumn(name = "user_id") , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> authorities;


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
