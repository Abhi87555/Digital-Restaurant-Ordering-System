package com.jack.sparrow.potc.restaurantmanagement.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jack.sparrow.potc.restaurantmanagement.exception.Error;
import com.jack.sparrow.potc.restaurantmanagement.model.Context;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    @JsonProperty("apiExecutionStatus")
    private String executionStatus;

    @JsonProperty("entity")
    private T entity;

    @JsonProperty("error")
    private Error error;

    @JsonProperty("context")
    private Context context;

    public String getExecutionStatus() {
        return executionStatus;
    }

    @JsonProperty("apiExecutionStatus")
    public void setExecutionStatus(String executionStatus) {
        this.executionStatus = executionStatus;
    }

    public T getEntity() {
        return entity;
    }

    @JsonProperty("entity")
    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Error getError() {
        return error;
    }

    @JsonProperty("error")
    public void setError(Error error) {
        this.error = error;
    }

    public Context getContext() {
        return context;
    }

    @JsonProperty("context")
    public void setContext(Context context) {
        this.context = context;
    }
}
