package com.myRetail.products.cassandra.repos;

import com.myRetail.products.cassandra.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface ProductRepository extends CrudRepository<Product, UUID> {

}

