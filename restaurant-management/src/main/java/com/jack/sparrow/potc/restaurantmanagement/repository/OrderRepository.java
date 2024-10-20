package com.jack.sparrow.potc.restaurantmanagement.repository;

import com.jack.sparrow.potc.restaurantmanagement.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Custom query methods can be defined here
    List<Order> findByCustomer_CustomerId(Long customerId);
    List<Order> findByTable_TableId(Long tableId);
}
