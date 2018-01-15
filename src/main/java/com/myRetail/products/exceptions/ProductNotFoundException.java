package com.myRetail.products.exceptions;

public class ProductNotFoundException extends ApiException {


    public ProductNotFoundException(String errorMessage) {
        super(errorMessage, "PRODUCT_NOT_FOUND");
    }

    public ProductNotFoundException(String errorMessage, String errorId) {
        super(errorMessage, errorId);
    }

    public ProductNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
