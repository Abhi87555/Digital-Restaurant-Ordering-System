package com.jack.sparrow.potc.restaurantmanagement.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jack.sparrow.potc.restaurantmanagement.model.BusinessEntity;
import com.jack.sparrow.potc.restaurantmanagement.model.Context;

public class Request<T extends BusinessEntity> {

    @JsonProperty("entity")
    private T entity;

    @JsonProperty("context")
    private Context context;

    public T getEntity() {
        return entity;
    }

    @JsonProperty("entity")
    public void setEntity(T entity) {
        this.entity = entity;
    }

    public Context getContext() {
        return context;
    }

    @JsonProperty("context")
    public void setContext(Context context) {
        this.context = context;
    }
}
