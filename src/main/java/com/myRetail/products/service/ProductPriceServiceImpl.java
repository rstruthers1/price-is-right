package com.myRetail.products.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.products.api.model.CurrentPrice;
import com.myRetail.products.api.model.ProductCurrentPriceResponse;
import com.myRetail.products.cassandra.domain.ProductPrice;
import com.myRetail.products.cassandra.repos.ProductPriceRepository;
import com.myRetail.products.exceptions.ProductNotFoundException;
import com.myRetail.products.exceptions.ProductPriceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
public class ProductPriceServiceImpl implements ProductPriceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String PRODUCT_WITH_ID_NOT_FOUND = "Product with id %d not found";

    private static final String PRODUCT_PRICE_WITH_ID_NOT_FOUND = "Price not found for product with id %d";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ProductPriceRepository productPriceRepository;

    @Override
    public ProductCurrentPriceResponse getCurrentProductPrice(Integer id) throws Exception {

        String productName = fetchProductName(id);
        ProductPrice productPrice = productPriceRepository.findOne(id);
        if (productPrice == null) {
            throw new ProductPriceNotFoundException(String.format(PRODUCT_PRICE_WITH_ID_NOT_FOUND, id));
        }

        ProductCurrentPriceResponse response = new ProductCurrentPriceResponse();
        response.setId(id);
        response.setName(productName);
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setCurrencyCode(productPrice.getCurrencyCode());
        currentPrice.setValue(productPrice.getPrice());
        response.setCurrentPrice(currentPrice);
        return response;
    }

    @Override
    public ProductCurrentPriceResponse updateCurrentProductPrice(Integer id, CurrentPrice currentPrice) throws Exception {
        String productName = fetchProductName(id);

        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(id);
        productPrice.setPrice(currentPrice.getValue());
        productPrice.setCurrencyCode(currentPrice.getCurrencyCode());

        ProductPrice updatedProductPrice = productPriceRepository.save(productPrice);
        ProductCurrentPriceResponse response = new ProductCurrentPriceResponse();
        response.setId(id);
        response.setName(productName);
        CurrentPrice responseCurrentPrice = new CurrentPrice();
        responseCurrentPrice.setCurrencyCode(updatedProductPrice.getCurrencyCode());
        responseCurrentPrice.setValue(updatedProductPrice.getPrice());
        response.setCurrentPrice(responseCurrentPrice);
        return response;
    }

    private String fetchProductName(Integer id) throws ProductNotFoundException {
        String url = "http://redsky.target.com/v2/pdp/tcin/" + id + "?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics";

        String product = null;

        try {
            product = restTemplate.getForObject(url, String.class);
        } catch (Exception ex) {
            logger.info(productWithIdNotFoundMessage(id), ex);
        }

        if (product == null) {
            logger.info(productWithIdNotFoundMessage(id));
            throw new ProductNotFoundException(productWithIdNotFoundMessage(id));
        }

        final ObjectMapper mapper = new ObjectMapper();
        JsonNode tree = null;
        try {
            tree = mapper.readTree(product);
        } catch (IOException e) {
            logger.info(productWithIdNotFoundMessage(id));
            throw new ProductNotFoundException(productWithIdNotFoundMessage(id));
        }

        JsonNode value = null;
        try {
            value = tree.findValue("title");
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new ProductNotFoundException(productWithIdNotFoundMessage(id));
        }

        if (value == null) {
            throw new ProductNotFoundException(productWithIdNotFoundMessage(id));
        }
        return value.asText();
    }

    private String productWithIdNotFoundMessage(Integer id) {
        return String.format(PRODUCT_WITH_ID_NOT_FOUND, id);
    }


}
