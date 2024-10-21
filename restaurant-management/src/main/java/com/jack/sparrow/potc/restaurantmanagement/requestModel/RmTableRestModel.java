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
public class RmTableRestModel implements RestModel {

    @JsonProperty("tableId")
    private long tableId;

    @JsonProperty("tableNumber")
    //@NotNull(message = "Table number cannot be null")
    private String tableNumber;

    @JsonProperty("tableStatus")
    //@PredefinedValues(value = {"AVAILABLE","OCCUPIED"}, message = "Invalid table status")
    private String tableStatus;
}
