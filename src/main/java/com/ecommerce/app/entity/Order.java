package com.ecommerce.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long orderId;

    private LocalDateTime ordersDate;

    private Double totalPrice;

    private String status;

    @ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "order" , cascade = {CascadeType.DETACH, CascadeType.MERGE , CascadeType.PERSIST, CascadeType.REFRESH})
    private List<OrderItem> orderItems;



}
