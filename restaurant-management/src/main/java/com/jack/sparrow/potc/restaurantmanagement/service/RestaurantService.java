package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.exception.Error;
import com.jack.sparrow.potc.restaurantmanagement.exception.RestaurantManagementException;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.*;
import com.jack.sparrow.potc.restaurantmanagement.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private TableService tableService;

    public Response addCuisine(CuisineRestModel request) {
        try {
            return createResponse(cuisineService.addCuisine(request));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response getCuisineByName(String cuisineName) {
        try {
            return createResponse(cuisineService.findByCuisineName(cuisineName));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response addItem(MenuRestModel request) {
        try {
            return createResponse(menuService.addItem(request));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response getMenuByCuisine(String cuisineName) {
        try {
            return createResponse(menuService.findByCuisine(cuisineName));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response getMenu() {
        try {
            return createResponse(menuService.getMenu());
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response createCart(CartRestModel request) {
        try {
            return createResponse(cartService.createCart(request));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response updateCart(Long cartId, CartRestModel request) {
        try {
            request.setCartId(cartId);
            return createResponse(cartService.updateCart(request));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response findbyCartId(Long cartId) {
        try {
            return createResponse(cartService.findByCartId(cartId));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response getAllCarts() {
        try {
            return createResponse(cartService.getAllCart());
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response placeOrder(OrderRestModel request) {
        try {
            return createResponse(orderService.createOrder(request));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response updateOrderStatus(Long orderId, OrderRestModel request) {
        try {
            request.setOrderId(orderId);
            return createResponse(orderService.updateOrder(request));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response findByOrderId(Long orderId) {
        try {
            return createResponse(orderService.findByOrderId(orderId));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response getAllOrders() {
        try {
            return createResponse(orderService.getAllOrder());
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response recordPayment(PaymentRestModel request) {
        try {
            return createResponse(paymentService.recordPayment(request));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response updatePayment(Long paymentId, PaymentRestModel request) {
        try {
            request.setPaymentId(paymentId);
            return createResponse(paymentService.updatePayment(request));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response getAllTable(){
        try {
            return createResponse(tableService.getAllTable());
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    public Response getTableByTableNumber(String tableNumber){
        try {
            return createResponse(tableService.getTableByTableNumber(tableNumber));
        } catch (Exception e) {
            return createResponse(e);
        }
    }

    private Response createResponse(RestModel restModel) {
        List<RestModel> restModelList = new ArrayList<>();
        restModelList.add(restModel);
        return new Response(restModelList);
    }

    private Response createResponse(List<RestModel> restModelList) {
        return new Response(restModelList);
    }

    private Response createResponse(Exception e) {
        Error error = e instanceof RestaurantManagementException ? ((RestaurantManagementException) e).getError()
                : new Error(e.getMessage());
        return new Response(error);
    }
}
