package com.tamnguyen.servicebooking.validators;

import com.tamnguyen.servicebooking.models.Leg;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidateDateForLeg implements ConstraintValidator<CheckDates, Leg> {
   @Override
    public boolean isValid(Leg leg, ConstraintValidatorContext context) {
        return leg.getDepartureTime().isBefore(leg.getArrivalTime());
    }
}