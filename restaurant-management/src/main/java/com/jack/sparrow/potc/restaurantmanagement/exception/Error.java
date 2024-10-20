package com.jack.sparrow.potc.restaurantmanagement.exception;

public class Error {

    private String errorCode;

    public Error(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
