package com.jack.sparrow.potc.restaurantmanagement.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jack.sparrow.potc.restaurantmanagement.model.Context;
import com.jack.sparrow.potc.restaurantmanagement.model.Cuisine;
import com.jack.sparrow.potc.restaurantmanagement.model.RestaurantUser;
import com.jack.sparrow.potc.restaurantmanagement.request.Request;
import com.jack.sparrow.potc.restaurantmanagement.service.MenuService;
import com.jack.sparrow.potc.restaurantmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @RequestMapping(value="/addCuisine", method= RequestMethod.POST, produces = "application/json"
    , consumes = "application/json")
    public void addCuisineToMenu(@RequestBody Request<Cuisine> request){
        Cuisine cuisine = request.getEntity();
        Context context = request.getContext();
        if(userService.getUserById(context.getUsername()).isAdmin()){
            menuService.addCuisine(cuisine);
        }else{
            throw new RuntimeException("user does not have admin access");
        }
    }

    @RequestMapping(value="/updateAvailability", method= RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public void updateAvailability(@RequestBody Request<Cuisine> request){
        Cuisine cuisine = request.getEntity();
        Context context = request.getContext();
        if(userService.getUserById(context.getUsername()).isAdmin()){
            menuService.updateAvailability(cuisine.getCuisineName(), cuisine.isIs_available());
        }else{
            throw new RuntimeException("user does not have admin access");
        }
    }

    @RequestMapping(value="/getAllCuisine", method= RequestMethod.POST,produces = "application/json"
            , consumes = "application/json")
    public List<Cuisine> getAllCuisinesFromMenu(){
        return menuService.getListOfCuisines();
    }

    @RequestMapping(value="/addUser", method= RequestMethod.POST,produces = "application/json"
            , consumes = "application/json")
    public void addUser(@RequestBody Request<RestaurantUser> request){
        RestaurantUser user = request.getEntity();
        Context context = request.getContext();
        if (userService.getAllUsers().isEmpty()){
            user.setAdmin(true);
            userService.addUser(user);
        }else if(userService.getUserById(context.getUsername()).isAdmin()){
            userService.addUser(user);
        }else{
            throw new RuntimeException("user does not have admin access");
        }
    }

    @RequestMapping(value="/updateUserAccess", method= RequestMethod.POST,produces = "application/json"
            , consumes = "application/json")
    public void updateUserAccess(@RequestBody Request<RestaurantUser> request){
        RestaurantUser user = request.getEntity();
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if(userById != null && userById.isAdmin()){
            userService.updateAccess(user);
        }else{
            throw new RuntimeException("user does not have admin access");
        }
    }

    @RequestMapping(value="/getAllUsers", method= RequestMethod.POST,produces = "application/json"
            , consumes = "application/json")
    public List<RestaurantUser> getAllUsers(){
        return userService.getAllUsers();
    }
}
