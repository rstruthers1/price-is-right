package com.myRetail.products.service;

import com.myRetail.products.api.model.ProductCurrentPriceResponse;
import com.myRetail.products.exceptions.ProductNotFoundException;

public interface ProductPriceService {
    ProductCurrentPriceResponse getCurrentProductPrice(Integer id) throws Exception;
}
