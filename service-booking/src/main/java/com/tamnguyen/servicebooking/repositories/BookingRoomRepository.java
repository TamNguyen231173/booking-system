package com.tamnguyen.servicebooking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tamnguyen.servicebooking.models.BookingRoom;

public interface BookingRoomRepository extends MongoRepository<BookingRoom, String> {
  
}
