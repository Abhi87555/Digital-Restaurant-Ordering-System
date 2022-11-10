package com.jack.sparrow.potc.restaurantmanagement.exception;

public class RestaurantManagementException extends RuntimeException{

    private Error error;

    public RestaurantManagementException(String message, Error error){
        super(message);
        this.error = error;
    }

    public RestaurantManagementException(String message, Throwable e, Error error){
        super(message, e);
        this.error = error;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
