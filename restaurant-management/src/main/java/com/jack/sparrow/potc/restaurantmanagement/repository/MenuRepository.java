package com.jack.sparrow.potc.restaurantmanagement.repository;

import com.jack.sparrow.potc.restaurantmanagement.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    // Custom query methods can be defined here
    List<Menu> findByCuisine_CuisineId(Long cuisineId);

    List<Menu> findByCuisine_CuisineIdAndPortion(Long cuisineId, String portion);
}
