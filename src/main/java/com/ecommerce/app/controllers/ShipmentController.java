package com.ecommerce.app.controllers;

import com.ecommerce.app.requests.SetShipmentStatus;
import com.ecommerce.app.responses.ResponseHandler;
import com.ecommerce.app.service.ShipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipment")
public class ShipmentController {
    private ShipmentService shipmentService;

    @Autowired
    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAllShipmentsByUserId(@PathVariable Long id, @RequestParam(name = "page", defaultValue = "0") int page){

        return ResponseHandler.generateResponse(200, shipmentService.findShipmentsByUserId(id, page));


    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> changeStatusOfShipment(@PathVariable Long id, @RequestBody SetShipmentStatus setShipmentStatus){

        return ResponseHandler.generateResponse(200, shipmentService.changeStatusOfShipmentByShipmentId(id , setShipmentStatus));
    }


}
