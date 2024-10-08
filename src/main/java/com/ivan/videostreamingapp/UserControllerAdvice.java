package com.ivan.videostreamingapp;

import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity<Map<String, String>> handleUnexpectedTypeException(UnexpectedTypeException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Validation error");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ClashingUserException.class)
    public ResponseEntity<Map<String, String>> handleUnexpectedTypeException(ClashingUserException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Clashing user error");
        errorResponse.put("message", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserUnderageException.class)
    public ResponseEntity<Map<String, String>> handleUnexpectedTypeException(UserUnderageException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "User underage error");
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

//    @ExceptionHandler(InvalidDateException.class)
//    public ResponseEntity<Map<String, String>> handleUnexpectedTypeException(InvalidDateException ex) {
//        Map<String, String> errorResponse = new HashMap<>();
//        errorResponse.put("error", "Invalid date error");
//        errorResponse.put("message", ex.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//    }

}
