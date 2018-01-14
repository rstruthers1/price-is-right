package com.myRetail.products.cassandra.repos;

import com.myRetail.products.cassandra.domain.Customer;

import org.springframework.data.repository.CrudRepository;



public interface CustomerRepository extends CrudRepository<Customer, String> {
	

}