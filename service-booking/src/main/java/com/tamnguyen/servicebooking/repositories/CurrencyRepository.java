package com.tamnguyen.servicebooking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tamnguyen.servicebooking.models.Currency;

public interface CurrencyRepository extends MongoRepository<Currency, String> {
  
}
