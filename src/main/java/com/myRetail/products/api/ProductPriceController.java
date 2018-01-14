package com.myRetail.products.api;


import java.util.concurrent.atomic.AtomicLong;

import com.myRetail.products.api.model.ProductCurrentPriceResponse;
import com.myRetail.products.cassandra.domain.ProductPrice;
import com.myRetail.products.cassandra.repos.ProductPriceRepository;
import com.myRetail.products.domain.Greeting;
import com.myRetail.products.service.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductPriceController {
    @Autowired
    ProductPriceRepository productPriceRepository;

    @Autowired
    ProductPriceService productPriceService;

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/products/{id}")
    public ResponseEntity<ProductCurrentPriceResponse> productPrice(@PathVariable Integer id) throws Exception {
        return new ResponseEntity<>(productPriceService.getCurrentProductPrice(id), HttpStatus.OK);
    }

//    @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
//    public ProductPrice updateProductPrice(@PathVariable Integer id) {
//        return productPriceRepository.save();
//    }
}
