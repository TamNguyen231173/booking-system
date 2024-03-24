package com.tamnguyen.servicebooking.configs;

import org.springframework.stereotype.Service;

import com.tamnguyen.servicebooking.repositories.AirportRepository;
import com.tamnguyen.servicebooking.repositories.CurrencyRepository;
import com.tamnguyen.servicebooking.repositories.RoomRepository;
import com.tamnguyen.servicebooking.repositories.VenderRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class initDB {
    private final RoomRepository roomRepository;
    private final VenderRepository venderRepository;
    private final AirportRepository airportRepository;
    private final CurrencyRepository currencyRepository;

    @PostConstruct
    public void init() {
      if (venderRepository.count() == 0) {
      }

      if (roomRepository.count() == 0) {
      }

      if (airportRepository.count() == 0) {
      }

      if (currencyRepository.count() == 0) {
      }
    }
}
