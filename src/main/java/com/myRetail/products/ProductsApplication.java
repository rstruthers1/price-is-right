package com.myRetail.products;


import com.datastax.driver.core.Session;
import com.myRetail.products.cassandra.domain.ProductPrice;
import com.myRetail.products.cassandra.repos.ProductPriceRepository;
import com.myRetail.products.util.PriceUtil;
import com.myRetail.products.util.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SpringBootApplication
public class ProductsApplication implements CommandLineRunner {
    @Autowired
    ProductPriceRepository productPriceRepository;

    @Autowired
    Session session;


    public static void main(String[] args) {
        SpringApplication.run(ProductsApplication.class, args);
    }

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder().requestFactory(SimpleClientHttpRequestFactory.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public void run(String... args) throws Exception {
        //session.execute("drop table ProductPrice");
//        clearData();
//        saveData();
        lookup();
    }

    public void clearData(){

        productPriceRepository.deleteAll();
    }

    public void saveData(){
        System.out.println("===================Save product prices to Cassandra===================");

        ProductPrice cust_1 = new ProductPrice(15117729, PriceUtil.getTwoDecimalPlaceValue(0.0), "USD");
        ProductPrice cust_2 = new ProductPrice(16483589, PriceUtil.getTwoDecimalPlaceValue(0.0), "USD");
        ProductPrice cust_3 = new ProductPrice(16696652, PriceUtil.getTwoDecimalPlaceValue(155.32), "USD");
        ProductPrice cust_4 = new ProductPrice(16752456, PriceUtil.getTwoDecimalPlaceValue(0.0), "USD");
        ProductPrice cust_5 = new ProductPrice(15643793, PriceUtil.getTwoDecimalPlaceValue(0.0), "USD");
        ProductPrice cust_6 = new ProductPrice(13860428, PriceUtil.getTwoDecimalPlaceValue(9.29), "USD");


        productPriceRepository.save(cust_1);
        productPriceRepository.save(cust_2);
        productPriceRepository.save(cust_3);
        productPriceRepository.save(cust_4);
        productPriceRepository.save(cust_5);
        productPriceRepository.save(cust_6);
    }



    public void lookup(){
        System.out.println("===================Lookup all product prices===================");
        Iterable<ProductPrice> customers = productPriceRepository.findAll();
        customers.forEach(System.out::println);

      
    }
}