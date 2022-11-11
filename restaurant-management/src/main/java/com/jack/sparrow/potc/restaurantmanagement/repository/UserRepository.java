package com.jack.sparrow.potc.restaurantmanagement.repository;


import com.jack.sparrow.potc.restaurantmanagement.model.RestaurantUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<RestaurantUser, String> {

    @Modifying
    @Query("update RestaurantUser u set u.isAdmin =:isAdmin where u.userName =:userName")
    void updateAccessById(@Param("isAdmin") boolean isAdmin, @Param("userName") String userName);
}
