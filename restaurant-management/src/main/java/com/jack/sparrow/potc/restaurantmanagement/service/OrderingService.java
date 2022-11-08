package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.model.Cart;
import com.jack.sparrow.potc.restaurantmanagement.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderingService {

    @Autowired
    @Qualifier("cartMap")
    private Map<String, Cart> cartMap;

    @Autowired
    private CartRepository cartRepository;

    public void placeOrder(String userName, long paidAmount){
        Cart cart = cartMap.get(userName);
        recordPayment(userName, paidAmount);
        cart.setPaid(true);
        cart.setOrderPlaced(true);
        cartRepository.save(cart);
        cartMap.remove(userName);
    }

    public long getTotalCartValue(String userName){
        return cartMap.get(userName).getTotalCost();
    }

    public void recordPayment(String userName, long paidAmount){
        long totalCartValue = getTotalCartValue(userName);
        if (totalCartValue > paidAmount){
            throw new RuntimeException("paidAmount is less than total cost of the cart");
        }else {
            System.out.println("payment Recorded Successfully");
            System.out.println("Returning extra amount : " + (paidAmount - totalCartValue));
        }
    }
}
