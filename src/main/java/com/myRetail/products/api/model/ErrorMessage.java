package com.myRetail.products.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Data
public class ErrorMessage {

    private Date timestamp;


    private String httpStatus;


    private String errorMessage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ValidationError> validationErrors;

    private String errorId;

    private String path;

    // Do not remove. Default constructor is needed
    public ErrorMessage() {}

    public ErrorMessage(HttpServletRequest httpRequest, String errorMessage, String errorId, HttpStatus httpStatus) {
        this(httpRequest, errorMessage, errorId, null, httpStatus);
    }

    public ErrorMessage(HttpServletRequest httpRequest, String errorMessage, String errorId,
                        List<ValidationError> validationErrors, HttpStatus httpStatus) {
        this.setTimestamp(new Date());
        this.setHttpStatus(httpStatus.value() + " - " + httpStatus.getReasonPhrase());
        this.setErrorMessage(errorMessage);
        this.setErrorId(errorId);
        this.setValidationErrors(validationErrors);
        this.setPath(httpRequest.getServletPath());
    }
}
