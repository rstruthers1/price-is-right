package com.myRetail.products.api;

import com.myRetail.products.TestUtils;
import com.myRetail.products.api.model.CurrentPrice;
import com.myRetail.products.api.model.ProductCurrentPriceResponse;
import com.myRetail.products.exceptions.ProductNotFoundException;
import com.myRetail.products.exceptions.ProductPriceNotFoundException;
import com.myRetail.products.service.ProductPriceService;
import com.myRetail.products.util.PriceUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ProductPriceControllerTest {

    @Mock
    ProductPriceService productPriceService;

    @InjectMocks
    ProductPriceController productPriceController;

    private MockMvc mockMvc;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);

        mockMvc = standaloneSetup(productPriceController)
                .setControllerAdvice(new ExceptionHandlerControllerAdvice())
                .build();
    }

    @Test
    public void productPriceTest() throws Exception {
        ProductCurrentPriceResponse productCurrentPriceResponse = new ProductCurrentPriceResponse();
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setValue(PriceUtil.getTwoDecimalPlaceValue(42.42));
        currentPrice.setCurrencyCode("USD");
        productCurrentPriceResponse.setCurrentPrice(currentPrice);
        Mockito.when(productPriceService.getCurrentProductPrice(3))
                .thenReturn(productCurrentPriceResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("http://mytest/products/3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void productPrice_ProductNotFound_Test() throws Exception {
        ProductCurrentPriceResponse productCurrentPriceResponse = new ProductCurrentPriceResponse();
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setValue(PriceUtil.getTwoDecimalPlaceValue(42.42));
        currentPrice.setCurrencyCode("USD");
        productCurrentPriceResponse.setCurrentPrice(currentPrice);
        Mockito.when(productPriceService.getCurrentProductPrice(3))
                .thenThrow(new ProductNotFoundException("Product with id 3 not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("http://mytest/products/3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void productPrice_ProducPricetNotFound_Test() throws Exception {
        ProductCurrentPriceResponse productCurrentPriceResponse = new ProductCurrentPriceResponse();
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setValue(PriceUtil.getTwoDecimalPlaceValue(42.42));
        currentPrice.setCurrencyCode("USD");
        productCurrentPriceResponse.setCurrentPrice(currentPrice);
        Mockito.when(productPriceService.getCurrentProductPrice(3))
                .thenThrow(new ProductPriceNotFoundException("Price not found for product with id 3"));

        mockMvc.perform(MockMvcRequestBuilders.get("http://mytest/products/3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateProductPrice() throws Exception {
        ProductCurrentPriceResponse productCurrentPriceResponse = new ProductCurrentPriceResponse();
        CurrentPrice currentPrice = new CurrentPrice();
        currentPrice.setValue(PriceUtil.getTwoDecimalPlaceValue(42.42));
        currentPrice.setCurrencyCode("USD");
        productCurrentPriceResponse.setCurrentPrice(currentPrice);
        Mockito.when(productPriceService.updateCurrentProductPrice(3, currentPrice))
                .thenReturn(productCurrentPriceResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("http://mytest/products/3")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.updatePriceRequest()))
                .andExpect(status().isOk());
    }

}
