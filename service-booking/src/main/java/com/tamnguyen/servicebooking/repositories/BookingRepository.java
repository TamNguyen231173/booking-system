package com.tamnguyen.servicebooking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tamnguyen.servicebooking.models.Booking;

public interface BookingRepository extends MongoRepository<Booking, String> {
}
