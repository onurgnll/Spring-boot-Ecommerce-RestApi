package com.ecommerce.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@NoArgsConstructor
@Table(name = "notification")
@Data
@AllArgsConstructor
public class Notification {


    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private boolean readed;


    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE , CascadeType.PERSIST , CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

}
