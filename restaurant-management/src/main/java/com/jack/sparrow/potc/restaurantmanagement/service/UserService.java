package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.model.RestaurantUser;
import com.jack.sparrow.potc.restaurantmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public void addUser(RestaurantUser user){
        repository.save(user);
    }

    public RestaurantUser getUserById(String userName){
        return repository.findById(userName).orElse(null);
    }

    public void updateAccess(RestaurantUser user){
        repository.updateAccessById(user.getUserName(), user.isAdmin());
    }

    public List<RestaurantUser> getAllUsers(){
        List<RestaurantUser> userList = new ArrayList<>();
        repository.findAll().forEach(userList::add);
        return userList;
    }
}
