package com.ecommerce.app.repos;

import com.ecommerce.app.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order , Long> {
    List<Order> findOrdersByUserUserId(Long id);
}
