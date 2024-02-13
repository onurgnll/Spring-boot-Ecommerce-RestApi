package com.ecommerce.app.controllers;

import com.ecommerce.app.requests.RegisterRequest;
import com.ecommerce.app.responses.ResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterRequest registerRequest){

        return ResponseHandler.generateResponse(200);

    }

}
