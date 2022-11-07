package com.jack.sparrow.potc.restaurantmanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "RestaurantUser")
public class RestaurantUser implements BusinessEntity{

    @Id
    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("isAdmin")
    private boolean isAdmin;

    public RestaurantUser() {
    }

    public RestaurantUser(String userName, String password, boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public String getUserName() {
        return userName;
    }

    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @JsonProperty("isAdmin")
    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
