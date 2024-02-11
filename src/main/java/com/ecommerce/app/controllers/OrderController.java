package com.ecommerce.app.controllers;

import com.ecommerce.app.requests.OrderCreateRequest;
import com.ecommerce.app.responses.ResponseHandler;
import com.ecommerce.app.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> getUsersOrders(@PathVariable Long id, @RequestParam(name = "page", defaultValue = "0") int page){
        return ResponseHandler.generateResponse(200,orderService.findOrdersByUserUserId(id , page));
    }

    @PostMapping
    public ResponseEntity<Object> getUsersOrders(@RequestBody OrderCreateRequest orderCreateRequest){
        return ResponseHandler.generateResponse(200,orderService.createOrder(orderCreateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> cancelOrderByOrderId(@PathVariable Long id){
        return ResponseHandler.generateResponse(200,orderService.cancelOrder(id));
    }
}
