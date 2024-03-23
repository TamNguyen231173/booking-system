package com.tamnguyen.servicebooking.services;

import org.springframework.stereotype.Service;

import com.tamnguyen.servicebooking.models.Room;
import com.tamnguyen.servicebooking.repositories.BookingRoomRepository;
import com.tamnguyen.servicebooking.repositories.RoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoomService {
  private RoomRepository roomRepository;
  private BookingRoomRepository bookingRoomRepository;

}
