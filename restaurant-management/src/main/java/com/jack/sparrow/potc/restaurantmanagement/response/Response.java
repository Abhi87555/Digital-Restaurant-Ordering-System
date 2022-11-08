package com.jack.sparrow.potc.restaurantmanagement.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jack.sparrow.potc.restaurantmanagement.model.BusinessEntity;
import com.jack.sparrow.potc.restaurantmanagement.model.Context;

import java.util.List;

public class Response<T extends BusinessEntity> {

    @JsonProperty("entity")
    private T entity;

    @JsonProperty("errors")
    private List<Error> errors;

    @JsonProperty("context")
    private Context context;

    public T getEntity() {
        return entity;
    }

    @JsonProperty("entity")
    public void setEntity(T entity) {
        this.entity = entity;
    }

    public List<Error> getErrors() {
        return errors;
    }

    @JsonProperty("errors")
    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public Context getContext() {
        return context;
    }

    @JsonProperty("context")
    public void setContext(Context context) {
        this.context = context;
    }
}
