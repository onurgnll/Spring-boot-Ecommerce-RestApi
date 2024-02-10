package com.ecommerce.app.requests;

import lombok.Data;

@Data
public class GetNotificationsRequest {
    private boolean getOnlyNotReadedNotifications;

}
