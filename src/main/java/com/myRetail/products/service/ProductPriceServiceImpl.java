package com.myRetail.products.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.products.api.model.CurrentPrice;
import com.myRetail.products.api.model.ProductCurrentPriceResponse;
import com.myRetail.products.cassandra.domain.ProductPrice;
import com.myRetail.products.cassandra.repos.ProductPriceRepository;
import com.myRetail.products.exceptions.ProductNotFoundException;
import com.myRetail.products.exceptions.ProductPriceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;

@Service
public class ProductPriceServiceImpl implements  ProductPriceService {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ProductPriceRepository productPriceRepository;

    @Override
    public ProductCurrentPriceResponse getCurrentProductPrice(Integer id) throws Exception{
        String url = "http://redsky.target.com/v2/pdp/tcin/" + id + "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";
        // product.item.product_description.title
        String product = restTemplate.getForObject(url, String.class);

        if (product == null) {
            throw new ProductNotFoundException("Product with id " +id + "not found");
        }
        final ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = null;
        try {
            tree = mapper.readTree(product);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ProductNotFoundException("Product with id " + id + "not found");
        }

        JsonNode value = null;
        try {
            value = tree.findValue("title");
            System.out.println("***** value: " + value);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ProductNotFoundException("Product with id " + id + "not found");
        }

        if (value == null) {
            throw new ProductNotFoundException("Product with id " + id + "not found");
        }

        ProductPrice productPrice = productPriceRepository.findOne(id);
        if (productPrice == null) {
            throw new ProductPriceNotFoundException("Price not found for product with id " + id);
        }

        ProductCurrentPriceResponse response = new ProductCurrentPriceResponse();
        response.setId(id);
        response.setName(value.asText());
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrencyCode(productPrice.getCurrencyCode());
        currentPrice.setValue(productPrice.getPrice());
        response.setCurrentPrice(currentPrice);
        return response;
    }
}
