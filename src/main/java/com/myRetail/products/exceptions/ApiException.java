package com.myRetail.products.exceptions;

public class ApiException extends Exception {

    private final String errorId;

    private final String errorMessage;

    public String getErrorId() {
        return errorId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public ApiException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorId = null;
    }

    public ApiException(String errorMessage, String errorId) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.errorId = errorId;
    }

    public ApiException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
        this.errorId = null;
    }
}