package com.jack.sparrow.potc.restaurantmanagement.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Error {

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("causedBy")
    private Error causedBy;

    public Error(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
