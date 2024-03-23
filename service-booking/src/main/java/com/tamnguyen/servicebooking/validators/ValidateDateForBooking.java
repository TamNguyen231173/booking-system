package com.tamnguyen.servicebooking.validators;

import com.tamnguyen.servicebooking.models.Booking;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateDateForBooking implements ConstraintValidator<CheckDates, Booking> {
   @Override
    public boolean isValid(Booking booking, ConstraintValidatorContext context) {
        return booking.getCheckInDate().isBefore(booking.getCheckOutDate());
    }
}
