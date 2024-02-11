package com.ecommerce.app.repos;

import com.ecommerce.app.entity.Shipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepo extends JpaRepository<Shipment , Long> {
    Page<Shipment> findShipmentsByUserUserId(Long id, Pageable pageable);
}
