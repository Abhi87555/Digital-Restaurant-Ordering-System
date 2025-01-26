package com.jack.sparrow.potc.restaurantmanagement.service;

import com.jack.sparrow.potc.restaurantmanagement.exception.RestaurantManagementException;
import com.jack.sparrow.potc.restaurantmanagement.model.*;
import com.jack.sparrow.potc.restaurantmanagement.repository.*;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class OrderService {

    Map<String, List<String>> validStatus = new HashMap<>();

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private RmTableRepository tableRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public OrderService() {
        validStatus.put("CONFIRMED", Collections.emptyList());
        validStatus.put("PREPARING", Collections.singletonList("CONFIRMED"));
        validStatus.put("SERVED", Collections.singletonList("PREPARING"));
        validStatus.put("COMPLETED", Arrays.asList("PREPARING", "SERVED"));
        validStatus.put("CANCELLED", Collections.singletonList("CONFIRMED"));
    }

    @Transactional
    public OrderRestModel createOrder(OrderRestModel orderObj) {
        validateOrder(orderObj);

        // get cart from carId
        Cart cart = cartRepository.findById(orderObj.getCartId()).get();

        Order order = new Order();

        // create order from cart
        RmTable table = cart.getTable();
        int totalItem = cart.getTotalItem();
        double totalCost = cart.getCartValue();
        String status = "CONFIRMED";
        Set<OrderItem> orderItems = new HashSet<>();

        cart.getCartItems()
                .forEach(cartItem -> {
                    OrderItemId orderItemId = new OrderItemId(order.getOrderId(), cartItem.getItem().getItemId());
                    OrderItem orderItem = new OrderItem(orderItemId, order, cartItem.getItem(), cartItem.getQuantity());
                    orderItems.add(orderItem);
                });

        // create customer
        Customer customer = customerRepository.findByEmail(orderObj.getCustomerEmail());
        if (customer == null) {
            customer = new Customer(orderObj.getCustomerName(), orderObj.getContactNo(), orderObj.getCustomerEmail());
        }

        order.setCustomer(customer);
        order.setTable(table);
        order.setTotalItem(totalItem);
        order.setTotalCost(totalCost);
        order.setOrderStatus(status);
        order.setOrderItems(orderItems);

        try {
            customerRepository.save(customer);
            orderRepository.save(order);
            cartRepository.delete(cart); // delete cart once order is placed
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while creating order", e);
        }
        orderObj.setOrderId(order.getOrderId());
        return orderObj;
    }

    public OrderRestModel findbyOrderId(long orderId) {
        try {
            Optional<Order> orderObj = orderRepository.findById(orderId);
            return orderObj.map(this::convertOrderToRestModel)
                    .orElseThrow(() -> new RuntimeException("Invalid order id"));
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while fetching order by orderId : " + orderId, e);
        }
    }

    @Transactional
    public OrderRestModel updateOrder(OrderRestModel orderObj) {
        validateUpdateOrder(orderObj);
        try {
            Order order = orderRepository.findById(orderObj.getOrderId()).get();
            if ("COMPLETED".equals(orderObj.getOrderStatus()) || "CANCELLED".equals(orderObj.getOrderStatus())) {
                RmTable table = order.getTable();
                if (table != null) {
                    table.setTableStatus("AVAILABLE");
                    tableRepository.save(table);
                }
            }
            order.setOrderStatus(orderObj.getOrderStatus());
            order.setUpdatedAt(new Timestamp(new Date().getTime()));
            return orderObj;
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while updating order", e);
        }
    }

    public RestModel findByOrderId(Long orderId) {
        try {
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            return orderOptional.map(this::convertOrderToRestModel)
                    .orElseThrow(() -> new RuntimeException("Invalid orderId"));
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while fetching all orders", e);
        }
    }

    public List<RestModel> getAllOrder() {
        try {
            List<Order> orderList = orderRepository.findAll();
            List<RestModel> orderRestModelList = new ArrayList<>();
            for (Order order : orderList) {
                orderRestModelList.add(convertOrderToRestModel(order));
            }
            return orderRestModelList;
        } catch (Exception e) {
            throw new RestaurantManagementException("Exception while fetching all orders", e);
        }
    }

    private void validateOrder(OrderRestModel orderObj) {
        if (orderObj.getCartId() == null) {
            throw new RestaurantManagementException("Cart Id cannot be null");
        }
        if (!cartRepository.findById(orderObj.getCartId()).isPresent()) {
            throw new RestaurantManagementException("Invalid cartId");
        }
        if (orderObj.getCustomerName() == null) {
            throw new RestaurantManagementException("Customer name cannot be null");
        }
        if (orderObj.getCustomerName().length() < 2 || orderObj.getCustomerName().length() > 50) {
            throw new RestaurantManagementException("Customer name length exceed, should be between 2 to 50 characters");
        }
        if (orderObj.getCustomerEmail() == null) {
            throw new RestaurantManagementException("Customer email cannot be null");
        }
        if (!isValidEmail(orderObj.getCustomerEmail())) {
            throw new RestaurantManagementException("Customer email is not a valid email id");
        }
        if (orderObj.getContactNo() != null && !isValidContactNo(orderObj.getContactNo())) {
            throw new RestaurantManagementException("Contact number is not a valid number");
        }
    }

    private void validateUpdateOrder(OrderRestModel orderObj) {
        if (orderObj.getOrderId() == null) {
            throw new RestaurantManagementException("Order id cannot be null");
        }
        Optional<Order> order = orderRepository.findById(orderObj.getOrderId());
        if (!order.isPresent()) {
            throw new RestaurantManagementException("Invalid order id");
        }
        if (orderObj.getOrderStatus() == null) {
            throw new RestaurantManagementException("Order status cannot be null");
        }
        if (!validStatus.containsKey(orderObj.getOrderStatus())) {
            throw new RestaurantManagementException("Invalid order status");
        }

        String orderStatus = order.get().getOrderStatus();
        if (!validStatus.get(orderObj.getOrderStatus()).contains(orderStatus)) {
            throw new RestaurantManagementException("Invalid order status transition from " +
                    orderStatus + " to " + orderObj.getOrderStatus());
        }
    }

    // Method to validate email
    private boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to validate contact number
    private boolean isValidContactNo(String contact) {
        Pattern pattern = Pattern.compile("^\\+?[0-9]{1,15}$");
        Matcher matcher = pattern.matcher(contact);
        return matcher.matches();
    }

    private OrderRestModel convertOrderToRestModel(Order order) {
        Set<OrderItem> orderItems = order.getOrderItems();
        List<OrderItemRestModel> orderItemRestModels = orderItems.stream()
                .map(this::convertOrderItemToRestModel)
                .collect(Collectors.toList());
        return new OrderRestModel(order.getOrderId(), null, order.getCustomer().getCustomerName(),
                order.getCustomer().getEmail(), order.getCustomer().getContactNo(),
                order.getTable().getTableNumber(), order.getOrderStatus(), orderItemRestModels,
                order.getTotalItem(), order.getTotalCost());
    }

    private OrderItemRestModel convertOrderItemToRestModel(OrderItem orderItem) {
        return new OrderItemRestModel(orderItem.getItem().getItemId(),
                orderItem.getItem().getCuisine().getCuisineName(),
                orderItem.getItem().getPortion(), orderItem.getQuantity(), orderItem.getItem().getPrice());
    }
}
