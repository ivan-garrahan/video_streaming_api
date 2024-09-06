package com.ivan.videostreamingapp;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class AgeValidator implements ConstraintValidator<AgeOver18, String> {

    @Override
    public void initialize(AgeOver18 constraintAnnotation) {
    }

    @Override
    public boolean isValid(String dobStr, ConstraintValidatorContext context) {
        if (dobStr == null || dobStr.isEmpty()) {
            return false; // or return true based on whether null values are considered valid in your context
        }

        try {
            LocalDate dob = LocalDate.parse(dobStr); // Parse the date
            LocalDate today = LocalDate.now();
            int age = Period.between(dob, today).getYears();
            if (age < 18) throw new UserUnderageException();
            return true;
//            return age >= 18; // Check if age is at least 18
        } catch (DateTimeParseException e) {
            return false; // Invalid date format
        }
    }
}
