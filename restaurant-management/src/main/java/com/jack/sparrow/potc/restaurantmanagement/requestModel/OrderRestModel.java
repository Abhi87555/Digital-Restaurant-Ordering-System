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
public class OrderRestModel implements RestModel {

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("cartId")
    private Long cartId;

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("customerEmail")
    private String customerEmail;

    @JsonProperty("contactNo")
    private String contactNo;

    @JsonProperty("tableNumber")
    private String tableNumber;

    @JsonProperty("orderStatus")
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
