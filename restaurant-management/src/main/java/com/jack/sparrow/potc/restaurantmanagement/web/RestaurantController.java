package com.jack.sparrow.potc.restaurantmanagement.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jack.sparrow.potc.restaurantmanagement.exception.Error;
import com.jack.sparrow.potc.restaurantmanagement.exception.RestaurantManagementException;
import com.jack.sparrow.potc.restaurantmanagement.model.Cart;
import com.jack.sparrow.potc.restaurantmanagement.model.Context;
import com.jack.sparrow.potc.restaurantmanagement.model.Cuisine;
import com.jack.sparrow.potc.restaurantmanagement.model.RestaurantUser;
import com.jack.sparrow.potc.restaurantmanagement.request.Request;
import com.jack.sparrow.potc.restaurantmanagement.response.Response;
import com.jack.sparrow.potc.restaurantmanagement.service.*;
import com.jack.sparrow.potc.restaurantmanagement.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    @RequestMapping(value = "/addCuisine", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<String> addCuisineToMenu(@RequestBody Request<Cuisine> request) {
        Response<String> response = new Response<>();
        try{
            service.addCuisineToMenu(request);
            response.setContext(response.getContext());
            response.setEntity("Cuisine Added to Menu!!!");
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/updateAvailability", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<String> updateAvailability(@RequestBody Request<Cuisine> request) {
        Response<String> response = new Response<>();
        try{
            service.updateAvailability(request);
            response.setContext(response.getContext());
            response.setEntity("updated Cuisine availability!!!");
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/getAllCuisine", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<List<Cuisine>> getAllCuisinesFromMenu() {
        Response<List<Cuisine>> response = new Response<>();
        try{
            response.setContext(new Context());
            response.setEntity(service.getAllCuisinesFromMenu());
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<String> addUser(@RequestBody Request<RestaurantUser> request) {
        Response<String> response = new Response<>();
        try{
            service.addUser(request);
            response.setContext(response.getContext());
            response.setEntity("user added successfully!!!");
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/updateUserAccess", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<String> updateUserAccess(@RequestBody Request<RestaurantUser> request) {
        Response<String> response = new Response<>();
        try{
            service.updateUserAccess(request);
            response.setContext(response.getContext());
            response.setEntity("updated user access successfully!!!");
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/getAllUsers", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<List<RestaurantUser>> getAllUsers() {
        Response<List<RestaurantUser>> response = new Response<>();
        try{
            response.setContext(new Context());
            response.setEntity(service.getAllUsers());
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/createCart", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<String> createCart(@RequestBody Request<Cart> request) {
        Response<String> response = new Response<>();
        try{
            service.createCart(request);
            response.setContext(response.getContext());
            response.setEntity("new cart is created for this user!!!");
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/addCuisines", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<String> addCuisines(@RequestBody Request<Cart> request) {
        Response<String> response = new Response<>();
        try{
            service.addCuisines(request);
            response.setContext(response.getContext());
            response.setEntity("Cuisines are added to the cart!!!");
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/deleteCuisine", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<String> deleteCuisine(@RequestBody Request<Cart> request) {
        Response<String> response = new Response<>();
        try{
            service.deleteCuisine(request);
            response.setContext(response.getContext());
            response.setEntity("Cuisines are deleted from the cart!!!");
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/getCart", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<Cart> getCartByUserName(@RequestBody Request<Cart> request) {
        Response<Cart> response = new Response<>();
        try{
            response.setContext(response.getContext());
            response.setEntity(service.getCartByUserName(request));
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/getAllCart", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<List<Cart>> getAllCarts(@RequestBody Request<Cart> request) {
        Response<List<Cart>> response = new Response<>();
        try{
            response.setContext(response.getContext());
            response.setEntity(service.getAllCarts(request));
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/getCartValue", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<Long> getCartValue(@RequestBody Request<Cart> request) {
        Response<Long> response = new Response<>();
        try{
            response.setContext(response.getContext());
            response.setEntity(service.getCartValue(request));
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }

    @RequestMapping(value = "/placeOrder", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response<String> placeOrder(@RequestBody Request<Cart> request) {
        Response<String> response = new Response<>();
        try{
            service.placeOrder(request);
            response.setContext(response.getContext());
            response.setEntity("Order is placed!!!");
            response.setExecutionStatus("Success");
        }catch (Exception e){
            response.setExecutionStatus("Failure");
            if (e instanceof RestaurantManagementException){
                response.setError(((RestaurantManagementException) e).getError());
            }else response.setError(new Error(e.getMessage()));
        }
        return response;
    }


}
