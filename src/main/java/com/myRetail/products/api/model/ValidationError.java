package com.myRetail.products.api.model;


import lombok.Data;

@Data
public class ValidationError {

    private String source;


    private String message;

    public ValidationError(String source, String message) {
        this.source = source;
        this.message = message;
    }
}
