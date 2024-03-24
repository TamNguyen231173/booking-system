package com.tamnguyen.servicebooking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tamnguyen.servicebooking.models.Airport;

public interface AirportRepository extends MongoRepository<Airport, String> {
  
}
