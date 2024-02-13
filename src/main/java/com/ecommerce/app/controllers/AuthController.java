package com.ecommerce.app.controllers;

import com.ecommerce.app.requests.LoginRequest;
import com.ecommerce.app.requests.RegisterRequest;
import com.ecommerce.app.requests.UserCreateRequest;
import com.ecommerce.app.responses.ResponseHandler;
import com.ecommerce.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller" , description = "expose auth endpoints")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserCreateRequest registerRequest){

        return ResponseHandler.generateResponse(200 , userService.createUser(registerRequest));

    }
    @PostMapping("/login")
    public ResponseEntity<Object> register(@RequestBody LoginRequest loginRequest){

        return ResponseHandler.generateResponse(200 , userService.loginUser(loginRequest));

    }

}
