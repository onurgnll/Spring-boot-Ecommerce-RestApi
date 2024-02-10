package com.ecommerce.app.repos;

import com.ecommerce.app.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {

    @Query(nativeQuery = true, value = "select * from notification where user_id = :userId and readed = 0 order by created_at desc")
    List<Notification> findNotReadedNotificationsByUserId(Long userId);



    @Query(nativeQuery = true, value = "select * from notification where user_id = :userId order by created_at desc")
    List<Notification> findNotificationsByUserId(Long userId);
}
