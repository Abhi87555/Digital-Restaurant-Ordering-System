package com.jack.sparrow.potc.restaurantmanagement.requestModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartItemRestModel implements RestModel {

    @JsonProperty("itemId")
    private Long itemId;

    @JsonProperty("cuisineName")
    private String cuisineName;

    @JsonProperty("portion")
    private String portion;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("price")
    private double price;

    @JsonProperty("isDeleted")
    private boolean isDeleted;
}
