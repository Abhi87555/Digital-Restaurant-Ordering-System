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
public class CartRestModel implements RestModel {

    @JsonProperty("cartId")
    private Long cartId;

    @JsonProperty("tableNumber")
    private String tableNumber;

    @JsonProperty("cartItems")
    private List<CartItemRestModel> cartItems;

    @JsonProperty("totalItems")
    private int totalItems;

    @JsonProperty("cartValue")
    private double cartValue;
}
