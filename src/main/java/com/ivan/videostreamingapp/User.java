package com.ivan.videostreamingapp;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;

public class User {

    private Long id;

    @NotBlank(message = "Username is required")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must be alphanumeric with no spaces")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "Password must contain at least one uppercase letter and one number")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Date of Birth is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Date format must match ISO 8601")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
//    @Past(message = "Date of Birth must be in the past")
    private String dob;

    @Pattern(regexp = "^\\d{16}$", message = "Credit Card Number must have 16 digits")
    private String creditCardNumber;

    public User() {}

    public User(String username, String password, String email, String dob) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
    }

    public User(String username, String password, String email, String dob, String creditCardNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.creditCardNumber = creditCardNumber;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
