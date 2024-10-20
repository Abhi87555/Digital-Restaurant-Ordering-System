package com.jack.sparrow.potc.restaurantmanagement.repository;

import com.jack.sparrow.potc.restaurantmanagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Custom query methods can be defined here
    Customer findByEmail(String email);
}
