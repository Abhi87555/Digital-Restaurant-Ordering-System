package com.jack.sparrow.potc.restaurantmanagement.web;

import com.jack.sparrow.potc.restaurantmanagement.exception.Error;
import com.jack.sparrow.potc.restaurantmanagement.model.Cart;
import com.jack.sparrow.potc.restaurantmanagement.model.Cuisine;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.*;
import com.jack.sparrow.potc.restaurantmanagement.service.RestaurantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    @RequestMapping(value = "/cuisine/addCuisine", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public CuisineRestModel addCuisine(@RequestBody CuisineRestModel request) {
        return service.addCuisine(request);
    }

    @RequestMapping(value = "/cuisine", method = RequestMethod.GET, produces = "application/json")
    public CuisineRestModel getCuisineByName(@RequestParam String cuisineName) {
        return service.getCuisineByName(cuisineName);
    }

    @RequestMapping(value = "/menu/addItem", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public MenuRestModel addItem(@RequestBody MenuRestModel request) {
        return service.addItem(request);
    }

    @RequestMapping(value = "/menu", method = RequestMethod.GET, produces = "application/json")
    public List<MenuRestModel> getItemByCuisine(@RequestParam String cuisineName) {
        return service.getMenuByCuisine(cuisineName);
    }

    @RequestMapping(value = "/cart/create", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public CartRestModel createCart(@RequestBody CartRestModel request) {
        return service.createCart(request);
    }

    @RequestMapping(value = "/cart/update", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public CartRestModel updateCart(@RequestBody CartRestModel request) {
        return service.updateCart(request);
    }

    @RequestMapping(value = "/cart", method = RequestMethod.GET, produces = "application/json")
    public List<CartRestModel> getAllCarts() {
        return service.getAllCarts();
    }

    @RequestMapping(value = "/order/placeOrder", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public OrderRestModel placeOrder(@RequestBody OrderRestModel request) {
        return service.placeOrder(request);
    }

    @RequestMapping(value = "/order/updateStatus", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public OrderRestModel updateOrderStatus(@RequestBody OrderRestModel request) {
        return service.updateOrderStatus(request);
    }

    @RequestMapping(value = "/order", method = RequestMethod.GET, produces = "application/json")
    public List<OrderRestModel> getAllOrders() {
        return service.getAllOrders();
    }

    @RequestMapping(value = "/payment/recordPayment", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public PaymentRestModel placeOrder(@RequestBody PaymentRestModel request) {
        return service.recordPayment(request);
    }

    @RequestMapping(value = "/payment/updatePaymentStatus", method = RequestMethod.POST, produces = "application/json"
            , consumes = "application/json")
    public PaymentRestModel updateOrderStatus(@RequestBody PaymentRestModel request) {
        return service.updatePayment(request);
    }
}
