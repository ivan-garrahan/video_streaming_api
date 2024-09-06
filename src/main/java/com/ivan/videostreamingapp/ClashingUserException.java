package com.ivan.videostreamingapp;

public class ClashingUserException extends RuntimeException {
    public ClashingUserException(String username) {
        super("Existing user found with username " + username);
    }
}
