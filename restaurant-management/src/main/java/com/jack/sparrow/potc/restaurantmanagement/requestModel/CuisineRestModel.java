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
public class CuisineRestModel {

    @JsonProperty("cuisineId")
    private Long cuisineId;

    @JsonProperty("cuisineName")
    private String cuisineName;

    @JsonProperty("cuisineDescription")
    private String cuisineDescription;
}
