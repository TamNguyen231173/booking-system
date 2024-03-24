package com.tamnguyen.servicebooking.configs;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.tamnguyen.servicebooking.enums.ServiceType;
import com.tamnguyen.servicebooking.models.Airport;
import com.tamnguyen.servicebooking.models.Room;
import com.tamnguyen.servicebooking.models.Vender;
import com.tamnguyen.servicebooking.models.Currency;
import com.tamnguyen.servicebooking.models.Flight;
import com.tamnguyen.servicebooking.models.Leg;
import com.tamnguyen.servicebooking.repositories.AirportRepository;
import com.tamnguyen.servicebooking.repositories.CurrencyRepository;
import com.tamnguyen.servicebooking.repositories.FlightRepository;
import com.tamnguyen.servicebooking.repositories.LegRepository;
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
    private final LegRepository legRepository;
    private final FlightRepository flightRepository;

    @PostConstruct
    public void init() {
      Faker faker = new Faker();

      if (venderRepository.count() == 0) {
        createVenders(faker, 5);
      }
    
      if (airportRepository.count() == 0) {
          createAirports(faker, 10);
      }

      if (roomRepository.count() == 0) {
        createRooms(faker, 10);
      }

      if (currencyRepository.count() == 0) {
        createCurrencies(faker, 10);
      }

      if (flightRepository.count() == 0) {
        createFlight(faker, 20);
      }

      if (legRepository.count() == 0) {
        createLegs(faker, 30);
      }
    }

    private void createVenders(Faker faker, int count) {
      for (int i = 1; i <= count; i++) {
          Vender vender = new Vender();
          vender.setName(faker.company().name());
          vender.setAddress(faker.address().fullAddress());
          vender.setPhone(faker.phoneNumber().phoneNumber());
          vender.setEmail(faker.internet().emailAddress());
          vender.setService(i % 2 == 0 ? ServiceType.HOTEL : ServiceType.FLIGHT);
          venderRepository.save(vender);
      }
  }

  private void createAirports(Faker faker, int count) {
      for (int i = 1; i <= count; i++) {
          Airport airport = new Airport();
          airport.setCode(faker.bothify("???###"));
          airport.setName(faker.space().planet());
          airport.setCountry(faker.address().fullAddress());
          airport.setCity(faker.address().city());
          airportRepository.save(airport);
      }
  }

  private void createRooms(Faker faker, int count) {
    List<Vender> venders = venderRepository.findAll();
    for (int i = 1; i <= count; i++) {
        Room room = new Room();
        room.setVender(venders.get(faker.number().numberBetween(0, venders.size())));
        room.setRoomNumber(faker.numerify("###"));
        room.setPricePerNight(faker.number().randomDouble(2, 100, 1000));
        room.setMaxGuests(faker.number().numberBetween(1, 6));
        roomRepository.save(room);
    }
  }

  private void createCurrencies(Faker faker, int count) {
    for(int i = 1; i <= count; i++) {
      Currency currency = new Currency();
      currency.setName(faker.currency().name());
      currency.setCode(faker.currency().code());
      currency.setExchangeRate(faker.number().randomDouble(2, 1, 10));
      currencyRepository.save(currency);
    }
  }

  private void createFlight(Faker faker, int count) {
    List<Vender> venders = venderRepository.findAll();
    for(int i = 1; i <= count; i++) {
      Flight flight = new Flight();
      flight.setVender(venders.get(faker.number().numberBetween(0, venders.size())));
      flight.setCode(faker.bothify("???###"));
      flight.setTicketPrice(faker.number().randomDouble(2, 100, 1000));

      Date departureDate = faker.date().future(100, TimeUnit.DAYS);
      LocalDateTime departureTime = LocalDateTime.ofInstant(departureDate.toInstant(), ZoneId.systemDefault());
      flight.setDepartureTime(departureTime);

      Date arrivalDate = Date.from(departureDate.toInstant().plus(faker.number().numberBetween(1, 10), ChronoUnit.DAYS));
      LocalDateTime arrivalTime = LocalDateTime.ofInstant(arrivalDate.toInstant(), ZoneId.systemDefault());
      flight.setArrivalTime(arrivalTime);

      flightRepository.save(flight);
    }
  }

  private void createLegs(Faker faker, int count) {
    List<Airport> airports = airportRepository.findAll();
    List<Flight> flights = flightRepository.findAll();

    for(int i = 1; i <= count; i++) {
      Leg leg = new Leg();

      // Randomly select a flight for the leg
      Flight flight = flights.get(faker.number().numberBetween(0, flights.size()));
      leg.setFlight(flight);

      // Set the departure and arrival airports
      Airport departureAirport = airports.get(faker.number().numberBetween(0, airports.size()));
      Airport arrivalAirport = airports.get(faker.number().numberBetween(0, airports.size()));
      leg.setDepartureAirport(departureAirport);
      leg.setArrivalAirport(arrivalAirport);

      // Set the departure and arrival times within the flight's departure and arrival times
      LocalDateTime flightDepartureTime = flight.getDepartureTime();
      LocalDateTime flightArrivalTime = flight.getArrivalTime();
      LocalDateTime legDepartureTime = flightDepartureTime.plusMinutes(faker.number().numberBetween(0, 60));
      LocalDateTime legArrivalTime = legDepartureTime.plusMinutes(faker.number().numberBetween(30, 120));
      if (legArrivalTime.isAfter(flightArrivalTime)) {
          legArrivalTime = flightArrivalTime;
      }
      leg.setDepartureTime(legDepartureTime);
      leg.setArrivalTime(legArrivalTime);

      legRepository.save(leg);
    }
  }
}
