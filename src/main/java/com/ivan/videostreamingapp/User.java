package com.ivan.videostreamingapp;

import jakarta.validation.constraints.*;

import java.util.Objects;

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
    @AgeOver18
    private String dob;

    @Pattern(regexp = "^\\d{16}$", message = "Credit Card Number must have 16 digits")
    private String ccn;

    public User() {}

    public User(String username, String password, String email, String dob) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
    }

    public User(String username, String password, String email, String dob, String ccn) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.dob = dob;
        this.ccn = ccn;
    }

    public User(User newUser) {
        this.id = newUser.getId();
        this.username = newUser.getUsername();
        this.password = newUser.getPassword();
        this.email = newUser.getEmail();
        this.dob = newUser.getDob();
        this.ccn = newUser.getCcn();
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

    public String getCcn() {
        return ccn;
    }

    public void setCcn(String ccn) {
        this.ccn = ccn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(email, user.email) &&
                Objects.equals(dob, user.dob) &&
                Objects.equals(ccn, user.ccn);
    }

    // Overriding hashCode method
    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, dob, ccn);
    }

//    @Override
//    public String toString() {
//        return "Employee{" + "id=" + this.id + ", firstName='" + this.firstName + '\'' + ", lastName='" + this.lastName
//                + '\'' + ", role='" + this.role + '\'' + '}';
//    }
}
