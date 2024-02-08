package com.ecommerce.app.requests;

import lombok.Data;

@Data
public class UserCreateRequest {

    private String name;
    private String password;
    private String email;

}
