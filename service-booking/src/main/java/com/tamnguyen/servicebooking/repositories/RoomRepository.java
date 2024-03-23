package com.tamnguyen.servicebooking.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tamnguyen.servicebooking.models.Room;

public interface RoomRepository extends MongoRepository<Room, String>{
  
}
