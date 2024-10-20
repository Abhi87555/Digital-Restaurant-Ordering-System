package com.jack.sparrow.potc.restaurantmanagement.repository;

import com.jack.sparrow.potc.restaurantmanagement.model.CartItem;
import com.jack.sparrow.potc.restaurantmanagement.model.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {
    // Custom query methods can be defined here
}

