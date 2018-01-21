package com.myRetail.products.api;


import com.myRetail.products.api.model.CurrentPrice;
import com.myRetail.products.api.model.ErrorMessage;
import com.myRetail.products.api.model.ProductCurrentPriceResponse;
import com.myRetail.products.service.ProductPriceService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductPriceController {

    @Autowired
    ProductPriceService productPriceService;

    @RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "Retrieve a product price based on product id", notes = "Returns a product price" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductCurrentPriceResponse.class),
            @ApiResponse(code = 403, message = "Product with id not found", response = ErrorMessage.class) })
    public ResponseEntity<ProductCurrentPriceResponse> productPrice(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(productPriceService.getCurrentProductPrice(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    @ApiOperation(value = "Update the price for a product", notes = "Returns the updated product price" )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = ProductCurrentPriceResponse.class),
            @ApiResponse(code = 403, message = "Product with id not found", response = ErrorMessage.class) })
    public ResponseEntity<ProductCurrentPriceResponse> updateProductPrice(@PathVariable Integer id,
                                           @Valid @RequestBody CurrentPrice currentPrice)  throws Exception{
        return new ResponseEntity<>(productPriceService.updateCurrentProductPrice(id, currentPrice), HttpStatus.OK);
    }
    
}
