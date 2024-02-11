package com.ecommerce.app.controllers;

import com.ecommerce.app.requests.GetNotificationsRequest;
import com.ecommerce.app.responses.ResponseHandler;
import com.ecommerce.app.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNotReadedNotifications(@PathVariable Long id, @RequestBody GetNotificationsRequest getNotificationsRequest, @RequestParam(name = "page", defaultValue = "0") int page){
        return ResponseHandler.generateResponse(200, notificationService.getUsersNotifications(id , getNotificationsRequest , page));
    }

}
