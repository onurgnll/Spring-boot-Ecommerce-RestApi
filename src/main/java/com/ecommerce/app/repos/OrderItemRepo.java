package com.ecommerce.app.repos;

import com.ecommerce.app.entity.OrderItem;
import com.ecommerce.app.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepo extends JpaRepository<OrderItem , OrderItemId> {
}
