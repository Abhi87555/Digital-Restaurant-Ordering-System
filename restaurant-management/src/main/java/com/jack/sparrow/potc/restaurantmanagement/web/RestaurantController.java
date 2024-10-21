package com.jack.sparrow.potc.restaurantmanagement.web;

import com.jack.sparrow.potc.restaurantmanagement.requestModel.*;
import com.jack.sparrow.potc.restaurantmanagement.response.Response;
import com.jack.sparrow.potc.restaurantmanagement.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    @RequestMapping(value = "/cuisine", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response addCuisine(@RequestBody CuisineRestModel request) {
        return service.addCuisine(request);
    }

    @RequestMapping(value = "/cuisine", method = RequestMethod.GET, produces = "application/json")
    public Response getCuisineByName(@RequestParam String cuisineName) {
        return service.getCuisineByName(cuisineName);
    }

    @RequestMapping(value = "/menu", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response addItem(@RequestBody MenuRestModel request) {
        return service.addItem(request);
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = "application/json")
    public Response getItemByCuisine(@RequestParam String cuisineName) {
        return service.getMenuByCuisine(cuisineName);
    }

    @RequestMapping(value = "/cart", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response createCart(@RequestBody CartRestModel request) {
        return service.createCart(request);
    }

    @RequestMapping(value = "/cart/{cartId}", method = RequestMethod.PATCH, produces = "application/json"
            , consumes = "application/json")
    public Response updateCart(@PathVariable Long cartId, @RequestBody CartRestModel request) {
        return service.updateCart(cartId, request);
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET, produces = "application/json")
    public Response getAllCarts() {
        return service.getAllCarts();
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response placeOrder(@RequestBody OrderRestModel request) {
        return service.placeOrder(request);
    }

    @RequestMapping(value = "/order/{orderId}", method = RequestMethod.PATCH, produces = "application/json"
            , consumes = "application/json")
    public Response updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderRestModel request) {
        return service.updateOrderStatus(orderId, request);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET, produces = "application/json")
    public Response getAllOrders() {
        return service.getAllOrders();
    }

    @RequestMapping(value = "/payment", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public Response placeOrder(@RequestBody PaymentRestModel request) {
        return service.recordPayment(request);
    }

    @RequestMapping(value = "/payment/{paymentId}", method = RequestMethod.PATCH, produces = "application/json"
            , consumes = "application/json")
    public Response updateOrderStatus(@PathVariable Long paymentId, @RequestBody PaymentRestModel request) {
        return service.updatePayment(paymentId, request);
    }
}
