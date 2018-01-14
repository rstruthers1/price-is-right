package com.myRetail.products.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProductCurrentPriceResponse {


    private Integer id;
    private String name;

    @JsonProperty("current_price")
    private CurrentPrice currentPrice;

}
