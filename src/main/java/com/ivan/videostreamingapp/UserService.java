package com.ivan.videostreamingapp;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();
    private final List<String> usernames = new ArrayList<>();
    private final List<String> ccns = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public List<User> getAllUsers() {
        return users;
    }

    public Optional<User> getUserById(Long id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst();
    }

    public Optional<User> getUserByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
    }

    public User createUser(User user) {
        user.setId(counter.getAndIncrement());
        users.add(user);
        usernames.add(user.getUsername());
        ccns.add(user.getCcn());
        return user;
    }

    public Optional<User> updateUser(Long id, User updatedUser) {
        return getUserById(id).map(existingUser -> {
            existingUser.setUsername(updatedUser.getUsername());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setDob(updatedUser.getDob());
            existingUser.setCcn(updatedUser.getCcn());
            return existingUser;
        });
    }

    public boolean deleteUser(Long id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    public boolean userExists(String username) {
        Optional<User> userOptional = getUserByUsername(username);

        return usernames.contains(username);
    }

    public List<User> getUsersByCreditCardFilter(String creditCardFilter) {
        if (creditCardFilter == null || creditCardFilter.isEmpty()) {
            return new ArrayList<>(users);
        }

        boolean hasCreditCard = creditCardFilter.equalsIgnoreCase("Yes");
        return users.stream()
                .filter(user -> (hasCreditCard && user.getCcn() != null && !user.getCcn().isEmpty()) ||
                        (!hasCreditCard && (user.getCcn() == null || user.getCcn().isEmpty())))
                .collect(Collectors.toList());
    }

    public boolean isCcnRegistered(String creditCardNumber) {
        return ccns.contains(creditCardNumber);
    }
}
