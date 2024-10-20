package com.jack.sparrow.potc.restaurantmanagement.requestModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentRestModel {

    @JsonProperty("paymentId")
    private Long paymentId;

    @JsonProperty("orderId")
    private Long orderId;

    @JsonProperty("paymentMethod")
    private String paymentMethod;

    @JsonProperty("paymentStatus")
    private String paymentStatus;

    @JsonProperty("amountPaid")
    private Double amountPaid;

    @JsonProperty("paymentDate")
    private Timestamp paymentDate;
}
