package com.myRetail.products.api;

import com.myRetail.products.api.model.ErrorMessage;
import com.myRetail.products.exceptions.ProductNotFoundException;
import com.myRetail.products.exceptions.ProductPriceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler({ProductNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleProductNotFoundException(ProductNotFoundException exception,
                                                                HttpServletRequest httpRequest) throws IOException {

        ErrorMessage errorMessage = new ErrorMessage(httpRequest, exception.getErrorMessage(),
                exception.getErrorId(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ProductPriceNotFoundException.class})
    public ResponseEntity<ErrorMessage> handleProductPriceNotFoundException(ProductPriceNotFoundException exception,
                                                                HttpServletRequest httpRequest) throws IOException {

        ErrorMessage errorMessage = new ErrorMessage(httpRequest, exception.getErrorMessage(),
                exception.getErrorId(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
