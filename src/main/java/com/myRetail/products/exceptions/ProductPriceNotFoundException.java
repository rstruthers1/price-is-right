package com.myRetail.products.exceptions;

public class ProductPriceNotFoundException extends ApiException {
    public ProductPriceNotFoundException(String errorMessage) {
        super(errorMessage, "PRODUCT_PRICE_NOT_FOUND");
    }

    public ProductPriceNotFoundException(String errorMessage, String errorId) {
        super(errorMessage, errorId);
    }

    public ProductPriceNotFoundException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
}
