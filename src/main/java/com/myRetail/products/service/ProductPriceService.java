package com.myRetail.products.service;

import com.myRetail.products.api.model.CurrentPrice;
import com.myRetail.products.api.model.ProductCurrentPriceResponse;
import com.myRetail.products.exceptions.ProductNotFoundException;

public interface ProductPriceService {
    ProductCurrentPriceResponse getCurrentProductPrice(Integer id) throws Exception;

    ProductCurrentPriceResponse updateCurrentProductPrice(Integer id, CurrentPrice currentPrice) throws Exception;
}
