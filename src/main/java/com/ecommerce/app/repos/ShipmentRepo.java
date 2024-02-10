package com.ecommerce.app.repos;

import com.ecommerce.app.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepo extends JpaRepository<Shipment , Long> {
    List<Shipment> findShipmentsByUserUserId(Long id);
}
