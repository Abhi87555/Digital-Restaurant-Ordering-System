package com.jack.sparrow.potc.restaurantmanagement.exception;

public class RestaurantManagementException extends RuntimeException {

    private Error error;

    public RestaurantManagementException(String message) {
        super(message);
        this.error = new Error(message);
    }

    public RestaurantManagementException(String message, Throwable cause) {
        super(message, cause);
        if (cause instanceof RestaurantManagementException) {
            this.error = new Error(message, ((RestaurantManagementException) cause).getError());
        } else {
            this.error = new Error(message, new Error(cause.getMessage()));
        }
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
