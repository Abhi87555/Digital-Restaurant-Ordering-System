package com.jack.sparrow.potc.restaurantmanagement.repository;

import com.jack.sparrow.potc.restaurantmanagement.model.Cuisine;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisineRepository extends CrudRepository<Cuisine, String> {

    @Modifying
    @Query("update Cuisine c set c.is_available =:is_available where c.cuisineName =:cuisineName")
    void updateAvailabilityById(@Param("cuisineName") String cuisineName, @Param("is_available") boolean is_available);

}
