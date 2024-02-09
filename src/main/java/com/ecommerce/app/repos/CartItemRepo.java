package com.ecommerce.app.repos;

import com.ecommerce.app.entity.CartItem;
import com.ecommerce.app.entity.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CartItemRepo extends JpaRepository<CartItem , CartItemId> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart WHERE user_id = :userId", nativeQuery = true)
    void deleteAllByUserId(Long userId);
}
