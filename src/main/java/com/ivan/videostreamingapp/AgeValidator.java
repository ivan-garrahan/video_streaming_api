package com.ivan.videostreamingapp;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class AgeValidator implements ConstraintValidator<AgeOver18, String> {

    // The initialize method is used to set up any necessary resources or configurations
    // In this case, it's empty because no specific initialization is required
    @Override
    public void initialize(AgeOver18 constraintAnnotation) {
    }

    // The isValid method contains the actual validation logic
    // It checks whether the provided date of birth (dobStr) represents a user who is at least 18 years old
    @Override
    public boolean isValid(String dobStr, ConstraintValidatorContext context) {
        // Check if the date of birth string is null or empty
        // Return false to indicate invalid input (or true if null values are acceptable)
        if (dobStr == null || dobStr.isEmpty()) {
            return false; // or return true based on whether null values are considered valid in your context
        }

        try {
            // Parse the date of birth string into a LocalDate object
            LocalDate dob = LocalDate.parse(dobStr);

            // Get the current date
            LocalDate today = LocalDate.now();

            // Calculate the age by finding the period (in years) between the date of birth and today
            int age = Period.between(dob, today).getYears();

            // If the calculated age is less than 18, throw a custom exception
            if (age < 18) throw new UserUnderageException();

            // Return true if the age is at least 18, indicating the input is valid
            return true;

        } catch (DateTimeParseException e) {
            // If the date of birth string cannot be parsed into a valid date, catch the exception
            // Return false to indicate an invalid date format
            return false; // Invalid date format
        }
    }
}
