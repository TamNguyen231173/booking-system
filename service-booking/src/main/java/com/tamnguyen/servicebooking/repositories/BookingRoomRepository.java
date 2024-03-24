package com.tamnguyen.servicebooking.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.tamnguyen.servicebooking.models.BookingRoom;

public interface BookingRoomRepository extends MongoRepository<BookingRoom, String> {
  @Query("{ 'room.id' : ?0, 'booking.checkInDate' : { $lt : ?2 }, 'booking.checkOutDate' : { $gt : ?1 }, 'isDeleted' : false }")
  List<BookingRoom> findOverlappingBookings(String roomId, LocalDateTime checkIn, LocalDateTime checkOut);

  @Query("{ 'booking.checkInDate' : { $lt : ?2 }, 'booking.checkOutDate' : { $gt : ?1 }, 'isDeleted' : false }")
  List<BookingRoom> findBookedRooms(LocalDateTime checkIn, LocalDateTime checkOut);
}
