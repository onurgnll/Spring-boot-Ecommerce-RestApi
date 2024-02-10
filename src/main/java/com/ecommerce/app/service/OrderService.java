package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.NotFoundException;
import com.ecommerce.app.entity.*;
import com.ecommerce.app.repos.OrderRepo;
import com.ecommerce.app.requests.OrderCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private OrderRepo orderRepo;

    private UserService userService;

    private NotificationService notificationService;

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    private OrderItemService orderItemService;

    @Autowired
    public void setOrderItemService(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public OrderService(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    public Order save(Order order) {
        return orderRepo.save(order);
    }

    public List<Order> getOrderList(Long id) {
        userService.findById(id);
        return userService.getUserOrders(id);
    }

    public Order findOrderById(Long id) {
        return orderRepo.findById(id).orElseThrow(()-> new NotFoundException("böyle bir order yok"));
    }

    private List<OrderItem> addCartItemsToOrder(List<CartItem> cartItems, Order order) {

        List<OrderItem> orderItems = new ArrayList<OrderItem>();

        cartItems.forEach(e -> {
            OrderItem orderItem = new OrderItem();
            OrderItemId orderItemId = new OrderItemId();
            orderItemId.setOrdersId(order.getOrderId());
            orderItemId.setProductId(e.getProduct().getProductId());

            orderItem.setOrderItemId(orderItemId);

            orderItem.setProduct(e.getProduct());
            orderItem.setQuantity(e.getQuantity());

            orderItem.setOrder(order);

            order.setTotalPrice(order.getTotalPrice() + (e.getProduct().getPrice() * e.getQuantity()));


            orderItems.add(orderItemService.save(orderItem));

            notificationService.createNotification(e.getProduct().getUser().getUserId(), order.getUser().getName() + " Kullanıcısı " + orderItem.getProduct().getName() + " Ürününden " + orderItem.getQuantity() + " adet satın aldı. En kısa sürede teslim etmen bekleniyor.");

        });

        return orderItems;
    }

    public Order createOrder(OrderCreateRequest orderCreateRequest) {

        User user = userService.findById(orderCreateRequest.getUserId());

        List<CartItem> cartItems = user.getCartItems();


        if(cartItems.isEmpty())
            throw new NotFoundException("sepet boş");

        Order order = new Order();
        order.setOrdersDate(LocalDateTime.now());
        order.setStatus("Bekliyor");
        order.setUser(user);
        order.setTotalPrice(0.0);

        Order savedOrder = save(order);

        order.setOrderItems(addCartItemsToOrder(cartItems, savedOrder));

        userService.clearCart(user.getUserId());
        return save(savedOrder);

    }


}
