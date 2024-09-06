package com.ivan.videostreamingapp;

// Exception raised if existing user found in Users List
public class ClashingUserException extends RuntimeException {
    public ClashingUserException(String username) {
        super("Existing user found with username " + username);
    }
}
