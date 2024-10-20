package com.jack.sparrow.potc.restaurantmanagement.repository;

import com.jack.sparrow.potc.restaurantmanagement.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Custom query methods can be defined here
    List<Payment> findByOrder_OrderId(Long orderId);
}
