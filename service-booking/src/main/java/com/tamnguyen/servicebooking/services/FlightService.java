package com.tamnguyen.servicebooking.services;

import org.springframework.stereotype.Service;

import com.tamnguyen.servicebooking.models.BookingFlight;
import com.tamnguyen.servicebooking.repositories.FlightRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightService {
  private FlightRepository flightRepository;
  private BookingFlight bookingFlight;

}
