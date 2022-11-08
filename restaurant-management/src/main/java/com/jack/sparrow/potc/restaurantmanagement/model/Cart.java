package com.jack.sparrow.potc.restaurantmanagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Cart")
public class Cart implements BusinessEntity{

    @Id
    @JsonProperty("cartId")
    private String cartId;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("cuisines")
    @ElementCollection
    @CollectionTable(name = "cuisines_table", joinColumns = @JoinColumn(name = "cartId"))
    @Column(name = "cuisines")
    private Set<String> cuisines;

    @JsonProperty("totalCost")
    private long totalCost;

    @JsonProperty("orderPlaced")
    private boolean orderPlaced;

    @JsonProperty("paid")
    private boolean paid;

    public Cart() {
    }

    public String getCartId() {
        return cartId;
    }

    @JsonProperty("cartId")
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getUserName() {
        return userName;
    }

    @JsonProperty("userName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getCuisines() {
        return cuisines;
    }

    @JsonProperty("cuisines")
    public void setCuisines(Set<String> cuisines) {
        this.cuisines = cuisines;
    }

    public long getTotalCost() {
        return totalCost;
    }

    @JsonProperty("totalCost")
    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isOrderPlaced() {
        return orderPlaced;
    }

    @JsonProperty("orderPlaced")
    public void setOrderPlaced(boolean orderPlaced) {
        this.orderPlaced = orderPlaced;
    }

    public boolean isPaid() {
        return paid;
    }

    @JsonProperty("paid")
    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
