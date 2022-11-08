package com.jack.sparrow.potc.restaurantmanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Context {

    @JsonProperty("user-name")
    private String username;

    public String getUsername() {
        return username;
    }

    @JsonProperty("user-name")
    public void setUsername(String username) {
        this.username = username;
    }
}
