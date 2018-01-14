package com.myRetail.products.cassandra.repos;

import com.myRetail.products.cassandra.domain.ProductPrice;

import org.springframework.data.repository.CrudRepository;



public interface ProductPriceRepository extends CrudRepository<ProductPrice, Integer> {
	

}