package com.ecommerce.app.service;

import com.ecommerce.app.entity.CartItem;
import com.ecommerce.app.entity.CartItemId;
import com.ecommerce.app.repos.CartItemRepo;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private CartItemRepo cartItemRepo;

    public CartItemService(CartItemRepo cartItemRepo) {
        this.cartItemRepo = cartItemRepo;
    }

    public CartItem save(CartItem cartItem){
        return cartItemRepo.save(cartItem);
    }

    public void deleteById(CartItemId cartItemId){
        cartItemRepo.deleteById(cartItemId);
    }

    public void deleteAllByUserId(Long id){
        cartItemRepo.deleteAllByUserId(id);
    }

}
