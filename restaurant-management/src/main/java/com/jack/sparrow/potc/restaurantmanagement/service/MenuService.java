package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.model.Cuisine;
import com.jack.sparrow.potc.restaurantmanagement.repository.CuisineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private CuisineRepository repository;

    public void addCuisine(Cuisine cuisine){
        repository.save(cuisine);
    }

    public void updateAvailability(String cuisineName, boolean availability){
        repository.updateAvailabilityById(cuisineName, availability);
    }

    public List<Cuisine> getListOfCuisines(){
        List<Cuisine> cuisineList = new ArrayList<>();
        repository.findAll().forEach(cuisineList::add);
        return cuisineList;
    }
}
