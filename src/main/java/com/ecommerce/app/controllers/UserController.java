package com.ecommerce.app.controllers;

import com.ecommerce.app.requests.UserCreateRequest;
import com.ecommerce.app.responses.ResponseHandler;
import com.ecommerce.app.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers(){

        return ResponseHandler.generateResponse(200,userService.findAll() );
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody UserCreateRequest userCreateRequest){
        return ResponseHandler.generateResponse(200, userService.createUser(userCreateRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id){
        return ResponseHandler.generateResponse(200, userService.findById(id));
    }


    @GetMapping("/{id}/products")
    public ResponseEntity<Object> getProductsById(@PathVariable Long id){
        return ResponseHandler.generateResponse(200, userService.findProductsById(id));
    }

}
