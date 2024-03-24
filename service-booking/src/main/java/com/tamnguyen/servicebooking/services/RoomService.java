package com.tamnguyen.servicebooking.services;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tamnguyen.servicebooking.DTOs.Rooms.AddRoomDTO;
import com.tamnguyen.servicebooking.DTOs.Rooms.BookRoomDTO;
import com.tamnguyen.servicebooking.DTOs.Rooms.UpdateRoomDTO;
import com.tamnguyen.servicebooking.enums.BookingStatus;
import com.tamnguyen.servicebooking.enums.RoomStatus;
import com.tamnguyen.servicebooking.models.Booking;
import com.tamnguyen.servicebooking.models.BookingRoom;
import com.tamnguyen.servicebooking.models.Room;
import com.tamnguyen.servicebooking.models.Vender;
import com.tamnguyen.servicebooking.repositories.BookingRepository;
import com.tamnguyen.servicebooking.repositories.BookingRoomRepository;
import com.tamnguyen.servicebooking.repositories.RoomRepository;
import com.tamnguyen.servicebooking.repositories.VenderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
  private final RoomRepository roomRepository;
  private final BookingRoomRepository bookingRoomRepository;
  private final VenderRepository venderRepository;
  private final BookingRepository bookingRepository;

  public List<Room> getAvailableRooms(LocalDateTime checkIn, LocalDateTime checkOut) {
    if (checkIn == null) {
        checkIn = LocalDateTime.now();
    }

    if (checkOut == null) {
        checkOut = LocalDateTime.now().plusDays(1);
    }

    List<String> bookedRoomIds = bookingRoomRepository.findBookedRooms(checkIn, checkOut)
        .stream()
        .map(bookingRoom -> bookingRoom.getRoom().getId())
        .collect(Collectors.toList());

    List<Room> availableRooms = roomRepository.findAvailableRooms(bookedRoomIds);

    return availableRooms;
  }

  public Room addRoom(AddRoomDTO room) {
    Vender vender = venderRepository.findById(room.getId_vender())
        .orElseThrow(() -> new RuntimeException("Vendor not found"));

    Room newRoom = Room.builder()
      .name(room.getName())
      .type(room.getType())
      .pricePerNight(room.getPricePerNight())
      .bedCount(room.getBedCount())
      .maxGuests(room.getMaxGuests())
      .status(room.getStatus())
      .vender(vender)
      .build();

    return roomRepository.save(newRoom);
  }

  public Room updateRoom(String roomId, UpdateRoomDTO payload) {
    Room room = roomRepository.findById(roomId)
        .orElseThrow(() -> new RuntimeException("Room not found"));

    Boolean isRoomAvailable = room.getStatus() == RoomStatus.AVAILABLE;

    if (!isRoomAvailable) {
      throw new RuntimeException("Room is not available for update");
    }

    room.setName(payload.getName());
    room.setType(payload.getType());
    room.setPricePerNight(payload.getPricePerNight());
    room.setBedCount(payload.getBedCount());
    room.setMaxGuests(payload.getMaxGuests());
    room.setStatus(payload.getStatus());
    room.setIsDeleted(payload.getIsDeleted());

    roomRepository.save(room);

    return room;
  }

  public void deleteRoom(String roomId) {
    Room room = roomRepository.findById(roomId)
        .orElseThrow(() -> new RuntimeException("Room not found"));

    room.setIsDeleted(true);
    roomRepository.save(room);
  }

  public BookingRoom bookRoom(String roomId, BookRoomDTO payload) {
    Room room = roomRepository.findById(roomId)
        .orElseThrow(() -> new RuntimeException("Room not found"));

    LocalDateTime checkIn = payload.getCheckIn();
    LocalDateTime checkOut = payload.getCheckOut();

    // Check if room is available in range of date
    var isRoomAvailable = isRoomAvailable(roomId, checkIn, checkOut);
    if (!isRoomAvailable) {
      throw new RuntimeException("Room is not available in this range of date");
    }

    // Calculate total price
    var daysBetween = ChronoUnit.DAYS.between(checkIn, checkOut);
    var totalPrice = daysBetween * room.getPricePerNight();

    Booking booking = Booking.builder()
      .checkInDate(checkIn)
      .checkOutDate(checkOut)
      .build();

    BookingRoom bookingRoom = BookingRoom.builder()
      .room(room)
      .booking(booking)
      .totalPrice(totalPrice)
      .build();

    bookingRoomRepository.save(bookingRoom);

    return bookingRoom;
  }

  public void cancelBookingRoom(String roomId, String bookingRoomId) {
    Room room = roomRepository.findById(roomId)
        .orElseThrow(() -> new RuntimeException("Room not found"));

    BookingRoom bookingRoom = bookingRoomRepository.findById(bookingRoomId)
        .orElseThrow(() -> new RuntimeException("Booking room not found"));

    room.setStatus(RoomStatus.AVAILABLE);
    roomRepository.save(room);

    bookingRoomRepository.delete(bookingRoom);

    // Update booking status
    Booking booking = bookingRoom.getBooking();
    booking.setStatus(BookingStatus.CANCELLED);
    bookingRepository.save(booking);
  }

  public boolean isRoomAvailable(String roomId, LocalDateTime checkIn, LocalDateTime checkOut) {
    List<BookingRoom> overlappingBookings = bookingRoomRepository.findOverlappingBookings(roomId, checkIn, checkOut);
    return overlappingBookings.isEmpty();
  }
}
