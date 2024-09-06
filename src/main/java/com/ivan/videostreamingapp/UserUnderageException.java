package com.ivan.videostreamingapp;

public class UserUnderageException extends RuntimeException {
    public UserUnderageException() {
        super("User DOB is under 18");
    }
}
