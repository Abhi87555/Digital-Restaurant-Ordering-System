package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.exception.RestaurantManagementException;
import com.jack.sparrow.potc.restaurantmanagement.model.Cuisine;
import com.jack.sparrow.potc.restaurantmanagement.repository.CuisineRepository;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.CuisineRestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CuisineService {

    @Autowired
    private CuisineRepository repository;

    public CuisineRestModel addCuisine(CuisineRestModel cuisineObj) {
        validateCuisine(cuisineObj);
        Cuisine cuisine = new Cuisine(cuisineObj.getCuisineName(), cuisineObj.getCuisineDescription());
        try {
            repository.save(cuisine);
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while adding cuisine", e);
        }
        cuisineObj.setCuisineId(cuisine.getCuisineId());
        return cuisineObj;
    }

    public CuisineRestModel findByCuisineId(Long cuisineId) {
        try {
            Optional<Cuisine> cuisineObj = repository.findById(cuisineId);
            return cuisineObj.map(cuisine -> new CuisineRestModel(cuisine.getCuisineId(), cuisine.getCuisineName(), cuisine.getCuisineDescription()))
                    .orElseThrow(() -> new RuntimeException("Invalid cuisineId"));
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while fetching cuisine by cuisineId : " + cuisineId, e);
        }
    }

    public CuisineRestModel findByCuisineName(String cuisineName) {
        try {
            Optional<Cuisine> cuisineObj = Optional.ofNullable(repository.findByCuisineName(cuisineName));
            return cuisineObj.map(cuisine -> new CuisineRestModel(cuisine.getCuisineId(), cuisine.getCuisineName(), cuisine.getCuisineDescription()))
                    .orElseThrow(() -> new RuntimeException("Invalid cuisine name"));
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while fetching cuisines", e);
        }
    }

    private void validateCuisine(CuisineRestModel cuisine) {
        if (cuisine.getCuisineId() != null) {
            throw new RestaurantManagementException("Cuisine Id is an auto generated field");
        }
        if (cuisine.getCuisineName() == null) {
            throw new RestaurantManagementException("Cuisine name cannot be null");
        }
        if (cuisine.getCuisineName().length() > 100) {
            throw new RestaurantManagementException("Cuisine name length exceeded, should be less tha 100 characters");
        }
        if (repository.findByCuisineName(cuisine.getCuisineName()) != null) {
            throw new RestaurantManagementException("Cuisine already exist with same name");
        }
    }
}
