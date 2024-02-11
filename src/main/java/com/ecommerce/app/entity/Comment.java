package com.ecommerce.app.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
public class Comment {

    @EmbeddedId
    private CommentId commentId;

    private String content;

    private LocalDateTime createdAt;


    @ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST ,CascadeType.REFRESH})
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne(cascade = {CascadeType.DETACH , CascadeType.MERGE , CascadeType.PERSIST ,CascadeType.REFRESH})
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

}
