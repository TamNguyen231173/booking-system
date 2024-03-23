package com.tamnguyen.servicebooking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tamnguyen.servicebooking.models.BookingFlight;

public interface BookingFlightRepository extends MongoRepository<BookingFlight, String> {
  
}
