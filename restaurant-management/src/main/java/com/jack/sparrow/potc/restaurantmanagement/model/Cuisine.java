package com.jack.sparrow.potc.restaurantmanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "Cuisine")
public class Cuisine implements BusinessEntity {

    @Id
    @JsonProperty("cuisineName")
    private String cuisineName;

    @JsonProperty("cost")
    private long cost;

    @JsonProperty("is_available")
    private boolean is_available;

    public Cuisine(){
    }

    public Cuisine(String cuisineName, long cost, boolean is_available) {
        this.cuisineName = cuisineName;
        this.cost = cost;
        this.is_available = is_available;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    @JsonProperty("cuisineName")
    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    public long getCost() {
        return cost;
    }

    @JsonProperty("cost")
    public void setCost(long cost) {
        this.cost = cost;
    }


    public boolean isIs_available() {
        return is_available;
    }

    @JsonProperty("is_available")
    public void setIs_available(boolean is_available) {
        this.is_available = is_available;
    }
}
