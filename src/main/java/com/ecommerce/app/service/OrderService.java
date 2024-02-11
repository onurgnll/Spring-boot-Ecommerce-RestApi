package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.NotFoundException;
import com.ecommerce.app.entity.*;
import com.ecommerce.app.repos.OrderRepo;
import com.ecommerce.app.requests.OrderCreateRequest;
import com.ecommerce.app.requests.SetShipmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private OrderRepo orderRepo;

    private UserService userService;

    private ShipmentService shipmentService;

    @Autowired
    public void setShipmentService(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

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
        List<OrderItem> orderItems = new ArrayList<>();

        // cartItems'in kopyasını oluştur
        List<CartItem> cartItemsCopy = new ArrayList<>(cartItems);

        for (CartItem e : cartItemsCopy){

            Shipment shipment = new Shipment();
            shipment.setLastUpdate(LocalDateTime.now());
            shipment.setUser(e.getProduct().getUser());
            shipment.setStatus(order.getStatus());
            shipment.setAddress(order.getUser().getAddress());

            OrderItemId orderItemId = new OrderItemId();
            orderItemId.setOrdersId(order.getOrderId());
            orderItemId.setProductId(e.getProduct().getProductId());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(orderItemId);

            orderItem.setProduct(e.getProduct());
            orderItem.setQuantity(e.getQuantity());
            orderItem.setOrder(order);

            order.setTotalPrice(order.getTotalPrice() + (e.getProduct().getPrice() * e.getQuantity()));

            orderItem.setShipment(shipment);


            orderItems.add(orderItemService.save(orderItem));

            notificationService.createNotification(e.getProduct().getUser().getUserId(), order.getUser().getName() + " Kullanıcısı " + orderItem.getProduct().getName() + " Ürününden " + orderItem.getQuantity() + " adet satın aldı. En kısa sürede teslim etmen bekleniyor.");
        }
        return orderItems;
    }


    public Order createOrder(OrderCreateRequest orderCreateRequest) {

        User user = userService.findById(orderCreateRequest.getUserId());

        if(user.getAddress() == null){
            throw new NotFoundException("You dont have added any address please add and retry");
        }
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


    public Order cancelOrder(Long orderId){
        Order order = findOrderById(orderId);


        order.setStatus("İptal Edildi");

        order.getOrderItems().forEach(orderItem -> {
            Long shipmentId= orderItem.getShipment().getShipmentId();
            shipmentService.changeStatusOfShipmentByShipmentId(shipmentId, new SetShipmentStatus("İptal Edildi"));
        });


        return save(order);

    }


    public Page<Order> findOrdersByUserUserId(Long id, int page) {

        Pageable pageable = PageRequest.of(page,10);

        return orderRepo.findOrdersByUserUserId(id,pageable);

    }
}
