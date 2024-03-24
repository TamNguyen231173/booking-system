package com.tamnguyen.servicebooking.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tamnguyen.servicebooking.DTOs.ResponseSuccess;
import com.tamnguyen.servicebooking.DTOs.Rooms.AddRoomDTO;
import com.tamnguyen.servicebooking.models.Room;
import com.tamnguyen.servicebooking.services.RoomService;

import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {
  private final RoomService roomService;

  @GetMapping("/available")
  public ResponseEntity<ResponseSuccess> getAvailableRooms(
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkIn,
    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOut) {

    List<Room> rooms = roomService.getAvailableRooms(checkIn, checkOut);

    return ResponseEntity.ok(
      ResponseSuccess.builder()
        .status("success")
        .message("Get available rooms")
        .data(rooms)
        .build());
  }
  

  @PostMapping("/add")
  public ResponseEntity<ResponseSuccess> addRoom(@RequestBody AddRoomDTO request) {
    Room room = roomService.addRoom(request);

    return ResponseEntity.ok(
      ResponseSuccess.builder()
        .status("success")
        .message("Room added")
        .data(room)
        .build());
  }

  @DeleteMapping("/{roomId}")
  public ResponseEntity<ResponseSuccess> deleteRoom(@PathVariable String roomId) {
    roomService.deleteRoom(roomId);

    return ResponseEntity.ok(
      ResponseSuccess.builder()
        .status("success")
        .message("Room deleted")
        .build());
  }
  
}
