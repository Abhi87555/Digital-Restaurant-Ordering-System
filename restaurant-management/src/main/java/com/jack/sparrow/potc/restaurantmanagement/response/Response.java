package com.jack.sparrow.potc.restaurantmanagement.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jack.sparrow.potc.restaurantmanagement.exception.Error;
import com.jack.sparrow.potc.restaurantmanagement.requestModel.RestModel;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response implements Serializable {

    @JsonProperty("results")
    private List<RestModel> results;

    @JsonProperty("error")
    private Error error;

    public Response(List<RestModel> results) {
        this.results = results;
    }

    public Response(Error error) {
        this.error = error;
    }

    public Response(List<RestModel> results, Error error) {
        this.results = results;
        this.error = error;
    }

    public List<RestModel> getResults() {
        return results;
    }

    public void setResults(List<RestModel> results) {
        this.results = results;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
