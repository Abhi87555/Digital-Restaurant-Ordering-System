package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.exception.RestaurantManagementException;
import com.jack.sparrow.potc.restaurantmanagement.model.*;
import com.jack.sparrow.potc.restaurantmanagement.repository.CartRepository;
import com.jack.sparrow.potc.restaurantmanagement.repository.MenuRepository;
import com.jack.sparrow.potc.restaurantmanagement.repository.RmTableRepository;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.CartItemRestModel;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.CartRestModel;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.RestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RmTableRepository tableRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Transactional
    public CartRestModel createCart(CartRestModel cartObj) {
        // validate cart input object
        validateCart(cartObj);
        RmTable table = null;
        if (cartObj.getTableNumber() != null) {
            table = tableRepository.findByTableNumber(cartObj.getTableNumber());
        }
        Cart cart = new Cart(table, new HashSet<>());
        List<CartItemRestModel> cartItemsObj = cartObj.getCartItems();
        Set<CartItem> cartItems = cart.getCartItems();
        double totalCartValue = 0;
        if (cartItemsObj != null) {
            for (CartItemRestModel cartItemObj : cartItemsObj) {
                Menu menu = menuRepository.findById(cartItemObj.getItemId()).get();
                CartItemId cartItemId = new CartItemId(cart.getCartId(), menu.getItemId());
                CartItem cartItem = new CartItem(cartItemId, cart, menu, cartItemObj.getQuantity());

                boolean isAdded = cartItems.add(cartItem);
                totalCartValue = isAdded ? (totalCartValue + menu.getPrice()) : totalCartValue;
            }
        }
        // calculate total items and cart value.
        cart.setTotalItem(cartItems.size());
        cart.setCartValue(totalCartValue);
        try {
            if (table != null) {
                table.setTableStatus("OCCUPIED");
                tableRepository.save(table);
            }
            cartRepository.save(cart);
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while creating cart", e);
        }
        cartObj.setCartId(cart.getCartId());
        return cartObj;
    }

    public CartRestModel updateCart(CartRestModel cartObj) {
        // validate cart input object
        validateUpdateCart(cartObj);

        Cart cart = cartRepository.findById(cartObj.getCartId()).get();
        Set<CartItem> cartItems = cart.getCartItems();

        List<CartItemRestModel> cartItemsObj = cartObj.getCartItems();

        double totalCartValue = cart.getCartValue();
        if (cartItemsObj != null) {
            for (CartItemRestModel cartItemObj : cartItemsObj) {
                Menu menu = menuRepository.findById(cartItemObj.getItemId()).get();
                CartItemId cartItemId = new CartItemId(cart.getCartId(), menu.getItemId());
                CartItem cartItem = new CartItem(cartItemId, cart, menu, cartItemObj.getQuantity());

                if (cartItemObj.isDeleted()) {
                    boolean removed = cartItems.remove(cartItem);
                    totalCartValue = removed ? (totalCartValue - menu.getPrice()) : totalCartValue;
                } else {
                    boolean added = cartItems.add(cartItem);
                    totalCartValue = added ? (totalCartValue + menu.getPrice()) : totalCartValue;
                }
            }
        }

        // calculate total items and cart value.
        cart.setTotalItem(cartItems.size());
        cart.setCartValue(totalCartValue);
        cart.setUpdatedAt(new Timestamp(new Date().getTime()));
        try {
            cartRepository.save(cart);
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while updating cart", e);
        }
        return cartObj;
    }

    public CartRestModel findByCartId(long cartId) {
        try {
            Optional<Cart> cartObj = cartRepository.findById(cartId);
            return cartObj.map(this::convertCartToRestModel)
                    .orElseThrow(() -> new RuntimeException("Invalid cart id"));
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while fetching cart by cartId :" + cartId, e);
        }
    }

    public List<RestModel> getAllCart() {
        try {
            List<Cart> cartList = cartRepository.findAll();
            List<RestModel> cartRestModelList = new ArrayList<>();
            for (Cart cart : cartList) {
                cartRestModelList.add(convertCartToRestModel(cart));
            }
            return cartRestModelList;
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while fetching carts", e);
        }
    }

    private CartItemRestModel convertCartItemToRestModel(CartItem cartItem) {
        return new CartItemRestModel(cartItem.getItem().getItemId(),
                cartItem.getItem().getCuisine().getCuisineName(),
                cartItem.getItem().getPortion(), cartItem.getQuantity(), cartItem.getItem().getPrice(),
                false);
    }

    private CartRestModel convertCartToRestModel(Cart cart) {
        Set<CartItem> cartItems = cart.getCartItems();
        List<CartItemRestModel> cartItemRestModels = cartItems.stream()
                .map(this::convertCartItemToRestModel)
                .collect(Collectors.toList());
        return new CartRestModel(cart.getCartId(), cart.getTable().getTableNumber(), cartItemRestModels,
                cart.getTotalItem(), cart.getCartValue());
    }

    private void validateCart(CartRestModel cartObj) {
        if (cartObj.getTableNumber() != null) {
            RmTable table = tableRepository.findByTableNumber(cartObj.getTableNumber());
            if (table == null) {
                throw new RestaurantManagementException("Invalid table number");
            }
        }
        List<CartItemRestModel> cartItems = cartObj.getCartItems();
        validateCartItems(cartItems);
    }

    private void validateUpdateCart(CartRestModel cartObj) {
        if (cartObj.getCartId() == null) {
            throw new RestaurantManagementException("Cart Id cannot be null while updating cart");
        }
        if (!cartRepository.findById(cartObj.getCartId()).isPresent()) {
            throw new RestaurantManagementException("Invalid cart id");
        }
        if (cartObj.getTableNumber() != null) {
            RmTable table = tableRepository.findByTableNumber(cartObj.getTableNumber());
            if (table == null) {
                throw new RestaurantManagementException("Invalid table number");
            }
        }
        List<CartItemRestModel> cartItems = cartObj.getCartItems();
        validateCartItems(cartItems);
    }

    private void validateCartItems(List<CartItemRestModel> cartItems) {
        if (cartItems != null) {
            for (CartItemRestModel cartItem : cartItems) {
                if (cartItem.getItemId() == null) {
                    throw new RestaurantManagementException("Item Id cannot be null");
                }
                if (!menuRepository.findById(cartItem.getItemId()).isPresent()) {
                    throw new RestaurantManagementException("Invalid item id");
                }
                if (cartItem.getQuantity() == null) {
                    throw new RestaurantManagementException("Item quantity cannot be null");
                }
                if (cartItem.getQuantity() <= 0) {
                    throw new RestaurantManagementException("Item quantity cannot be 0 or less than 0");
                }
            }
        }
    }
}
