package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.model.Cart;
import com.jack.sparrow.potc.restaurantmanagement.model.Cuisine;
import com.jack.sparrow.potc.restaurantmanagement.repository.CuisineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CartService {

    @Autowired
    @Qualifier("cartMap")
    private Map<String, Cart> cartMap;

    @Autowired
    private CuisineRepository cuisineRepository;

    public void createNewCart(String userName){
        Cart cart = new Cart();
        String uniqueId = createUniqueId();
        cart.setCartId(uniqueId);
        cart.setUserName(userName);
        cart.setCuisines(new HashSet<>());
        cart.setOrderPlaced(false);
        cart.setTotalCost(0);
        cartMap.put(userName, cart);
    }

    public void addCuisines(Cart input){
        if (!cartMap.containsKey(input.getUserName())){
            createNewCart(input.getUserName());
        }
        Cart cart = cartMap.get(input.getUserName());
        input.getCuisines().stream()
                .map(cuisine -> cuisineRepository.findById(cuisine))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Cuisine::isIs_available)
                .filter(cuisine -> !cart.getCuisines().contains(cuisine.getCuisineName()))
                .filter(cuisine -> cart.getCuisines().add(cuisine.getCuisineName()))
                .forEach(cuisine -> cart.setTotalCost(cart.getTotalCost() + cuisine.getCost()));
        cartMap.put(cart.getUserName(), cart);
    }

    public void deleteCuisine(Cart input){
        if (!cartMap.containsKey(input.getUserName())){
            throw new RuntimeException("cart is not created for this user");
        }
        Cart cart = cartMap.get(input.getUserName());
        input.getCuisines().stream()
                .map(cuisine -> cuisineRepository.findById(cuisine))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(cuisine -> cart.getCuisines().remove(cuisine.getCuisineName()))
                .forEach(cuisine -> cart.setTotalCost(cart.getTotalCost() - cuisine.getCost()));
        cartMap.put(cart.getUserName(), cart);
    }

    public Cart getCartByUserName(String userName){
        return cartMap.get(userName);
    }

    public List<Cart> getAllCart(){
        return new ArrayList<>(cartMap.values());
    }

    private  String createUniqueId() {
        String alpha = "abcdefghijklmnpqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ0123456789";
        char[] uuid = alpha.toCharArray();
        Random rand = new Random();
        return IntStream.range(0, 8).parallel().mapToObj(i -> uuid[rand.nextInt(uuid.length)]).map(String::valueOf).collect(Collectors.joining(""));
    }
}
