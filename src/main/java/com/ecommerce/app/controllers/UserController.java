package com.ecommerce.app.controllers;

import com.ecommerce.app.requests.AddToCartRequest;
import com.ecommerce.app.requests.AddToFavoritesRequest;
import com.ecommerce.app.requests.RemoveFromCartList;
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

    @GetMapping("/{id}/favorites")
    public ResponseEntity<Object> getFavorites(@PathVariable Long id){
        return ResponseHandler.generateResponse(200, userService.getUserFavorites(id));
    }

    @PostMapping("/{id}/favorites")
    public ResponseEntity<Object> addToFavorites(@RequestBody AddToFavoritesRequest addToFavoritesRequest, @PathVariable Long id){


        userService.addToFavorites(addToFavoritesRequest, id);
            return ResponseHandler.generateResponse(200);

    }

    @DeleteMapping("/{id}/favorites")
    public ResponseEntity<Object> deleteFromFavorites(@RequestBody AddToFavoritesRequest addToFavoritesRequest, @PathVariable Long id){


        userService.deleteFromFavorites(addToFavoritesRequest, id);
        return ResponseHandler.generateResponse(200);

    }
    @PostMapping("/{id}/cart")
    public ResponseEntity<Object> addToCart(@RequestBody AddToCartRequest addToCartRequest, @PathVariable Long id){


        return ResponseHandler.generateResponse(200, userService.addToCart(addToCartRequest, id));

    }

    @GetMapping("/{id}/cart")
    public ResponseEntity<Object> getUserCart(@PathVariable Long id){
        return ResponseHandler.generateResponse(200, userService.findAllCartItemsById(id));
    }



    @DeleteMapping("/{id}/cart")
    public ResponseEntity<Object> deleteFromCart(@RequestBody RemoveFromCartList removeFromCartList, @PathVariable Long id){


        userService.removeFromCartList(removeFromCartList, id);
        return ResponseHandler.generateResponse(200);

    }
    @DeleteMapping("/{id}/cart/clear")
    public ResponseEntity<Object> clearCart(@PathVariable Long id){


        userService.clearCart(id);
        return ResponseHandler.generateResponse(200);

    }



}
