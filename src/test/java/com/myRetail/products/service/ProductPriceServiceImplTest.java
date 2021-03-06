package com.myRetail.products.service;

import com.google.common.io.Resources;
import com.myRetail.products.TestUtils;
import com.myRetail.products.api.model.CurrentPrice;
import com.myRetail.products.api.model.ProductCurrentPriceResponse;
import com.myRetail.products.cassandra.domain.ProductPrice;
import com.myRetail.products.cassandra.repos.ProductPriceRepository;
import com.myRetail.products.util.PriceUtil;
import com.myRetail.products.util.ProductPriceConstants;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;

public class ProductPriceServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Mock
    ProductPriceRepository productPriceRepository;

    @InjectMocks
    ProductPriceServiceImpl productPriceService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getCurrentProductPriceTest() {
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(3);
        productPrice.setCurrencyCode("USD");
        productPrice.setPrice(PriceUtil.getTwoDecimalPlaceValue(42.42));
        Mockito.when(productPriceRepository.findOne(3)).thenReturn(productPrice);

        String url = String.format(ProductPriceConstants.PRODUCT_SERVICE_URL, 3);
        String json = TestUtils.productJsonWithTitle("test product name");
        Mockito.when(restTemplate.getForObject(url, String.class)).thenReturn(json);

        ProductCurrentPriceResponse currentProductPriceResponse = null;
        try {
            currentProductPriceResponse = productPriceService.getCurrentProductPrice(3);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Should not throw an exception");
        }
        assertEquals("USD", currentProductPriceResponse.getCurrentPrice().getCurrencyCode());
        assertEquals("test product name", currentProductPriceResponse.getName());
    }

    @Test
    public void getCurrentProductPrice_ProductPriceNotFound_Test() {

        Mockito.when(productPriceRepository.findOne(3)).thenReturn(null);

        String url = String.format(ProductPriceConstants.PRODUCT_SERVICE_URL, 3);
        String json = TestUtils.productJsonWithTitle("test product name");
        Mockito.when(restTemplate.getForObject(url, String.class)).thenReturn(json);

        ProductCurrentPriceResponse currentProductPriceResponse = null;
        try {
            currentProductPriceResponse = productPriceService.getCurrentProductPrice(3);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals("Price not found for product with id 3", e.getMessage());
        }

    }

    @Test
    public void getCurrentProductPrice_ExternalCallThrowsException_Test() {

        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(3);
        productPrice.setCurrencyCode("USD");
        productPrice.setPrice(PriceUtil.getTwoDecimalPlaceValue(42.42));
        Mockito.when(productPriceRepository.findOne(3)).thenReturn(productPrice);


        String url = String.format(ProductPriceConstants.PRODUCT_SERVICE_URL, 3);
        String json = TestUtils.productJsonWithTitle("test product name");
        Mockito.when(restTemplate.getForObject(url, String.class)).thenThrow(new RuntimeException("ERROR!"));

        ProductCurrentPriceResponse currentProductPriceResponse = null;
        try {
            currentProductPriceResponse = productPriceService.getCurrentProductPrice(3);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals("Product with id 3 not found", e.getMessage());
        }
    }

    @Test
    public void getCurrentProductPrice_ExternalBadJsonReturned_Test() {

        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(3);
        productPrice.setCurrencyCode("USD");
        productPrice.setPrice(PriceUtil.getTwoDecimalPlaceValue(42.42));
        Mockito.when(productPriceRepository.findOne(3)).thenReturn(productPrice);


        String url = String.format(ProductPriceConstants.PRODUCT_SERVICE_URL, 3);
        String badJson = "-- very bad json--";
        Mockito.when(restTemplate.getForObject(url, String.class)).thenReturn(badJson);

        ProductCurrentPriceResponse currentProductPriceResponse = null;
        try {
            currentProductPriceResponse = productPriceService.getCurrentProductPrice(3);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals("Product with id 3 not found", e.getMessage());
        }
    }

    @Test
    public void getCurrentProductPrice_JsonHasNoTitle_Test() {

        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(3);
        productPrice.setCurrencyCode("USD");
        productPrice.setPrice(PriceUtil.getTwoDecimalPlaceValue(42.42));
        Mockito.when(productPriceRepository.findOne(3)).thenReturn(productPrice);


        String url = String.format(ProductPriceConstants.PRODUCT_SERVICE_URL, 3);
        String jsonNoTitle = "{}";
        Mockito.when(restTemplate.getForObject(url, String.class)).thenReturn(jsonNoTitle);

        ProductCurrentPriceResponse currentProductPriceResponse = null;
        try {
            currentProductPriceResponse = productPriceService.getCurrentProductPrice(3);
            fail("Should have thrown an exception");
        } catch (Exception e) {
            assertEquals("Product with id 3 not found", e.getMessage());
        }
    }

    @Test
    public void updateCurrentProductPriceTest() {
        ProductPrice productPrice = new ProductPrice();
        productPrice.setProductId(3);
        productPrice.setCurrencyCode("USD");
        productPrice.setPrice(PriceUtil.getTwoDecimalPlaceValue(42.42));

        ProductPrice updatedProductPrice = new ProductPrice();
        updatedProductPrice.setProductId(3);
        updatedProductPrice.setCurrencyCode("USD");
        updatedProductPrice.setPrice(PriceUtil.getTwoDecimalPlaceValue(42.42));
        Mockito.when(productPriceRepository.save(productPrice)).thenReturn(updatedProductPrice);

        String url = String.format(ProductPriceConstants.PRODUCT_SERVICE_URL, 3);
        String json = TestUtils.productJsonWithTitle("test product name");
        Mockito.when(restTemplate.getForObject(url, String.class)).thenReturn(json);

        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setValue(PriceUtil.getTwoDecimalPlaceValue(42.42));
        currentPrice.setCurrencyCode("USD");

        ProductCurrentPriceResponse productCurrentPriceResponse = null;

        try {
            productCurrentPriceResponse = productPriceService.updateCurrentProductPrice(3, currentPrice);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Should not throw an exception");
        }

        assertEquals("USD", productCurrentPriceResponse.getCurrentPrice().getCurrencyCode());
        assertEquals("test product name", productCurrentPriceResponse.getName());
    }


}
