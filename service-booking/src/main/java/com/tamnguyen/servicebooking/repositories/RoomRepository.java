package com.tamnguyen.servicebooking.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tamnguyen.servicebooking.models.Room;

public interface RoomRepository extends MongoRepository<Room, String>{
  @Query("{ 'id' : { $nin : ?0 }, 'status' : 'AVAILABLE' }")
  List<Room> findAvailableRooms(List<String> bookedRoomIds);
}
