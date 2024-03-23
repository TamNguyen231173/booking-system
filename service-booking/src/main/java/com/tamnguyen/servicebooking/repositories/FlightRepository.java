package com.tamnguyen.servicebooking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tamnguyen.servicebooking.models.Flight;

public interface FlightRepository extends MongoRepository<Flight, String>{
  
}
