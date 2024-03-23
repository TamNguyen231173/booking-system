package com.tamnguyen.servicebooking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tamnguyen.servicebooking.models.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {
  
}
