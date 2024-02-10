package com.ecommerce.app.service;

import com.ecommerce.app.entity.Notification;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.repos.NotificationRepo;
import com.ecommerce.app.requests.GetNotificationsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    private NotificationRepo notificationRepo;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public NotificationService(NotificationRepo notificationRepo) {
        this.notificationRepo = notificationRepo;
    }


    public List<Notification> findAllNotificationByUserId(Long userId){
        return notificationRepo.findNotificationsByUserId(userId);
    }

    public Notification save(Notification notification){
        return notificationRepo.save(notification);
    }

    public List<Notification> getUsersNotifications(Long id, GetNotificationsRequest getNotificationsRequest) {
        List<Notification> list;

        if (getNotificationsRequest.isGetOnlyNotReadedNotifications()) {
            list = notificationRepo.findNotReadedNotificationsByUserId(id);
        } else {
            list = findAllNotificationByUserId(id);
        }

        // Orijinal listenin kopyasını tutacak boş bir liste oluştur
        List<Notification> originalList = new ArrayList<>();

        // Liste üzerinde döngü yaparak her bir öğenin kopyasını al ve originalList'e ekle
        for (Notification notification : list) {
            originalList.add(new Notification(notification.getNotificationId(),notification.getContent(),notification.getCreatedAt(),notification.isReaded(), notification.getUser()));

            if (!notification.isReaded()) {
                notification.setReaded(true);
                save(notification);
            }
        }

        // Orijinal listeyi döndür
        return originalList;
    }


    public Notification createNotification(Long userId , String content){
        Notification notification = new Notification();
        User user = userService.findById(userId);
        notification.setContent(content);
        notification.setReaded(false);
        notification.setUser(user);
        notification.setCreatedAt(LocalDateTime.now());

        save(notification);

        return notification;

    }


}
