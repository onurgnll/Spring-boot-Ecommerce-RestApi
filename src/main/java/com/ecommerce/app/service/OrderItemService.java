package com.ecommerce.app.service;

import com.ecommerce.app.entity.OrderItem;
import com.ecommerce.app.repos.OrderItemRepo;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    private OrderItemRepo orderItemRepo;

    public OrderItemService(OrderItemRepo orderItemRepo) {
        this.orderItemRepo = orderItemRepo;
    }

    public OrderItem save(OrderItem orderItem){
        return orderItemRepo.save(orderItem);
    }

}
