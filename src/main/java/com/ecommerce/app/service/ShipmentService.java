package com.ecommerce.app.service;

import com.ecommerce.app.Exceptions.NotFoundException;
import com.ecommerce.app.entity.Order;
import com.ecommerce.app.entity.Shipment;
import com.ecommerce.app.repos.ShipmentRepo;
import com.ecommerce.app.requests.SetShipmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ShipmentService {
    private ShipmentRepo shipmentRepo;
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public ShipmentService(ShipmentRepo shipmentRepo) {
        this.shipmentRepo = shipmentRepo;
    }


    public Shipment save(Shipment shipment) {
        return shipmentRepo.save(shipment);
    }


    public List<Shipment> findShipmentsByUserId(Long id) {
        return shipmentRepo.findShipmentsByUserUserId(id);

    }

    public Shipment changeStatusOfShipmentByShipmentId(Long id, SetShipmentStatus setShipmentStatus) {

        String status = setShipmentStatus.getStatus();
        System.out.println(status);
        if (!Arrays.asList("Bekliyor", "Onaylandı", "Kargoya Verildi", "Teslim Edildi", "İptal Edildi").contains(status)) {
            throw new RuntimeException("This value is not valid!");
        }

        Shipment shipment = findById(id);
        shipment.setStatus(status);
        shipment.setLastUpdate(LocalDateTime.now());

        if (status.equals("Teslim Edildi")){
            Order order = shipment.getOrderItem().getOrder();
            order.setStatus("Tamamlandı");
            orderService.save(order);
        };

        return shipment;
    }

    private Shipment findById(Long id) {
        return shipmentRepo.findById(id).orElseThrow(() -> new NotFoundException("Not found shipment by given id: " + id));
    }
}
