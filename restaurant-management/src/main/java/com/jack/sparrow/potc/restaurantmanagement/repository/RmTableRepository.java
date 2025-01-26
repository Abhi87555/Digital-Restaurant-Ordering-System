package com.jack.sparrow.potc.restaurantmanagement.repository;

import com.jack.sparrow.potc.restaurantmanagement.model.RmTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RmTableRepository extends JpaRepository<RmTable, Long> {

    // Custom query methods can be defined here
    RmTable findByTableNumber(String tableNumber);
}
