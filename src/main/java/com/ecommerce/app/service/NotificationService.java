package com.ecommerce.app.service;

import com.ecommerce.app.entity.Notification;
import com.ecommerce.app.entity.User;
import com.ecommerce.app.repos.NotificationRepo;
import com.ecommerce.app.requests.GetNotificationsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Notification> getUsersNotifications(Long id, GetNotificationsRequest getNotificationsRequest , int page) {
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


        Pageable pageable = PageRequest.of(page,10);

        int start = page;

        int end = Math.min((start + pageable.getPageSize()), originalList.size());

        return new PageImpl<>(originalList.subList(start,end),pageable,originalList.size());
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
