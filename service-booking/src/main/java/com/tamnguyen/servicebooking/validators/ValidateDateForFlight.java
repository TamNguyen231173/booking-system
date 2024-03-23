package com.tamnguyen.servicebooking.validators;

import com.tamnguyen.servicebooking.models.Flight;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateDateForFlight implements ConstraintValidator<CheckDates, Flight> {
  @Override
   public boolean isValid(Flight flight, ConstraintValidatorContext context) {
       return flight.getDepartureTime().isBefore(flight.getArrivalTime());
   }
}
