package com.myRetail.products.cassandra.domain;

import com.myRetail.products.util.RandomGenerator;
import lombok.*;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

import java.math.BigDecimal;

@Table
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ProductPrice {
	
	@PrimaryKey
	private Integer productId;
	private BigDecimal price;
	private String currencyCode;
	
	public ProductPrice(){
		productId = RandomGenerator.getRandomInteger();
	}
	
	public ProductPrice(Integer productId, BigDecimal price, String currencyCode){
		this.productId = productId;
		this.price = price;
		this.currencyCode = currencyCode;
	}
	

}
