package com.myRetail.products.api;


import com.myRetail.products.api.model.CurrentPrice;
import com.myRetail.products.api.model.ProductCurrentPriceResponse;
import com.myRetail.products.service.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ProductPriceController {

    @Autowired
    ProductPriceService productPriceService;

    @RequestMapping("/products/{id}")
    public ResponseEntity<ProductCurrentPriceResponse> productPrice(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(productPriceService.getCurrentProductPrice(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ProductCurrentPriceResponse> updateProductPrice(@PathVariable Integer id,
                                           @Valid @RequestBody CurrentPrice currentPrice)  throws Exception{
        return new ResponseEntity<>(productPriceService.updateCurrentProductPrice(id, currentPrice), HttpStatus.OK);
    }
}
