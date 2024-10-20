package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.model.Cuisine;
import com.jack.sparrow.potc.restaurantmanagement.model.Menu;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private CuisineService cuisineService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    public CuisineRestModel addCuisine(CuisineRestModel request){
        return cuisineService.addCuisine(request);
    }

    public CuisineRestModel getCuisineByName(String cuisineName){
        return cuisineService.findByCuisineName(cuisineName);
    }

    public MenuRestModel addItem(MenuRestModel request){
        return menuService.addItem(request);
    }

    public List<MenuRestModel> getMenuByCuisine(String cuisineName){
        return menuService.findByCuisine(cuisineName);
    }

    public CartRestModel createCart(CartRestModel request){
        return cartService.createCart(request);
    }

    public CartRestModel updateCart(CartRestModel request){
        return cartService.updateCart(request);
    }

    public List<CartRestModel> getAllCarts(){
        return cartService.getAllCart();
    }

    public OrderRestModel placeOrder(OrderRestModel request){
        return orderService.createOrder(request);
    }

    public OrderRestModel updateOrderStatus(OrderRestModel request){
        return orderService.updateOrder(request);
    }

    public List<OrderRestModel> getAllOrders(){
        return orderService.getAllOrder();
    }

    public PaymentRestModel recordPayment(PaymentRestModel request){
        return paymentService.recordPayment(request);
    }

    public PaymentRestModel updatePayment(PaymentRestModel request){
        return paymentService.updatePayment(request);
    }
}
