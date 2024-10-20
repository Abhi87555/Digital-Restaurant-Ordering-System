package com.jack.sparrow.potc.restaurantmanagement.requestModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderRestModel {

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("cartId")
    private Long cartId;

    @JsonProperty("customerName")
    //@NotNull(message = "Customer name cannot be null")
    //@Size(min = 2, max = 50, message = "Customer name should be between 3 to 50 characters")
    private String customerName;

    @JsonProperty("customerEmail")
    //@NotNull(message = "Customer email cannot be null")
    //@Email(message = "Invalid email format")
    private String customerEmail;

    @JsonProperty("contactNo")
    //@Pattern(regexp = "^\\+?[0-9]{1,15}$", message = "Invalid contact number format")
    private String contactNo;

    @JsonProperty("tableNumber")
    //@Size(max = 2, message = "Table number length exceed, should be less than 3 characters")
    private String tableNumber;

    @JsonProperty("orderStatus")
    //@PredefinedValues(value = {"PENDING", "CONFIRMED", "PREPARING", "SERVED", "DELIVERED","COMPLETED"}, message = "Invalid order status")
    private String orderStatus;

    @JsonProperty("orderItems")
    private List<OrderItemRestModel> orderItems;

    @JsonProperty("totalItems")
    private Integer totalItems;

    @JsonProperty("totalCost")
    private Double totalCost;

    public OrderRestModel(Long orderId, String orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }
}
