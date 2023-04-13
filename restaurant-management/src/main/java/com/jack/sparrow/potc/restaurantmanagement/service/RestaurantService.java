package com.jack.sparrow.potc.restaurantmanagement.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jack.sparrow.potc.restaurantmanagement.exception.Error;
import com.jack.sparrow.potc.restaurantmanagement.exception.RestaurantManagementException;
import com.jack.sparrow.potc.restaurantmanagement.model.Cart;
import com.jack.sparrow.potc.restaurantmanagement.model.Context;
import com.jack.sparrow.potc.restaurantmanagement.model.Cuisine;
import com.jack.sparrow.potc.restaurantmanagement.model.RestaurantUser;
import com.jack.sparrow.potc.restaurantmanagement.request.Request;
import com.jack.sparrow.potc.restaurantmanagement.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderingService orderingService;

    public void addCuisineToMenu(Request<Cuisine> request){
        Validator.validateAddCuisineToMenuAndUpdateAvailability(request);
        Cuisine cuisine = request.getEntity();
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if (userById == null){
            throw new RestaurantManagementException("user not found!!!", new Error("user not found!!!"));
        }
        if (userById.isAdmin()) {
            menuService.addCuisine(cuisine);
        } else {
            throw new RestaurantManagementException("user does not have admin access", new Error("user does not have admin access"));
        }
    }

    public void updateAvailability(Request<Cuisine> request){
        Validator.validateAddCuisineToMenuAndUpdateAvailability(request);
        Cuisine cuisine = request.getEntity();
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if (userById == null){
            throw new RestaurantManagementException("user not found!!!", new Error("user not found!!!"));
        }
        if (userById.isAdmin()) {
            menuService.updateAvailability(cuisine.getCuisineName(), cuisine.isIs_available());
        } else {
            throw new RestaurantManagementException("user does not have admin access", new Error("user does not have admin access"));
        }
    }

    public List<Cuisine> getAllCuisinesFromMenu(){
        return menuService.getListOfCuisines();
    }

    public void addUser(Request<RestaurantUser> request){
        Validator.validateAddUser(request);
        RestaurantUser user = request.getEntity();
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if (userService.getAllUsers().isEmpty()) {
            user.setAdmin(true);
            userService.addUser(user);
        }else if(userById == null){
            throw new RestaurantManagementException("admin not found!!!", new Error("admin not found"));
        } else if (userById.isAdmin()) {
            userService.addUser(user);
        } else {
            throw new RestaurantManagementException("user does not have admin access", new Error("user does not have admin access"));
        }
    }

    public void updateUserAccess(Request<RestaurantUser> request){
        Validator.validateUpdateUseAccess(request);
        RestaurantUser user = request.getEntity();
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if (userById == null){
            throw new RestaurantManagementException("admin not found!!!", new Error("admin not found"));
        }
        if (userById.isAdmin()) {
            if (userService.updateAccess(user) == 0) {
                throw new RestaurantManagementException("User not found", new Error("User not found"));
            }
        } else {
            throw new RestaurantManagementException("user does not have admin access", new Error("user does not have admin access"));
        }
    }

    public List<RestaurantUser> getAllUsers(){
        return userService.getAllUsers();
    }

    public void createCart(Request<Cart> request){
        Validator.validateCreateAndGetCartAndGetCartValueAndPlaceOrder(request);
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if (userById != null) {
            cartService.createNewCart(request.getEntity().getUserName());
        } else {
            throw new RestaurantManagementException("user not found!!!", new Error("user not found!!!"));
        }
    }

    public void addCuisines(Request<Cart> request){
        Validator.validateAddAndDeleteCuisines(request);
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if (userById != null) {
            cartService.addCuisines(request.getEntity());
        } else {
            throw new RestaurantManagementException("user not found!!!", new Error("user not found!!!"));
        }
    }

    public void deleteCuisine(Request<Cart> request){
        Validator.validateAddAndDeleteCuisines(request);
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if (userById != null) {
            cartService.deleteCuisine(request.getEntity());
        } else {
            throw new RestaurantManagementException("user not found!!!", new Error("user not found!!!"));
        }
    }

    public Cart getCartByUserName(Request<Cart> request){
        Validator.validateCreateAndGetCartAndGetCartValueAndPlaceOrder(request);
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if (userById != null) {
            return cartService.getCartByUserName(request.getEntity().getUserName());
        } else {
            throw new RestaurantManagementException("user not found!!!", new Error("user not found!!!"));
        }
    }

    public List<Cart> getAllCarts(Request<Cart> request){
        Validator.validateContext(request);
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if (userById == null){
            throw new RestaurantManagementException("admin not found!!!", new Error("admin not found"));
        }
        if (userById.isAdmin()) {
            return cartService.getAllCart();
        } else {
            throw new RestaurantManagementException("user does not have admin access", new Error("user does not have admin access"));
        }
    }

    public long getCartValue(Request<Cart> request){
        Validator.validateCreateAndGetCartAndGetCartValueAndPlaceOrder(request);
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if (userById != null) {
            return orderingService.getTotalCartValue(request.getEntity().getUserName());
        } else {
            throw new RestaurantManagementException("user not found!!!", new Error("user not found!!!"));
        }
    }

    public void placeOrder(Request<Cart> request){
        Validator.validateCreateAndGetCartAndGetCartValueAndPlaceOrder(request);
        Context context = request.getContext();
        RestaurantUser userById = userService.getUserById(context.getUsername());
        if (userById != null) {
            orderingService.placeOrder(request.getEntity().getUserName(), request.getEntity().getTotalCost());
        } else {
            throw new RestaurantManagementException("user not found!!!", new Error("user not found!!!"));
        }
    }
}
