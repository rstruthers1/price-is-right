package com.myRetail.products.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CurrentPrice {
    private BigDecimal value;

    @JsonProperty("currency_code")
    private String currencyCode;
}
