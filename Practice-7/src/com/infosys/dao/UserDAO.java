package com.infosys.dao;

import com.infosys.pojo.User;
import com.infosys.exceptions.InvalidUsernameException;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private List<User> users = new ArrayList<>();
    private static final int MAX_USERS = 5;

    public void addUser(User user) throws InvalidUsernameException {
        if (users.size() >= MAX_USERS) {
            throw new InvalidUsernameException("Cannot add more users. Maximum limit of " + MAX_USERS + " users reached.");
        }
        if (findUserByUsernameInternal(user.getUserName()) != null) {
            throw new InvalidUsernameException("User already exists.");
        }
        users.add(user);
        System.out.println("User added successfully. Total users: " + users.size());
    }

    public User findUserByUsername(String username) throws InvalidUsernameException {
        System.out.println("Looking for user in DAO: " + username);  // Debug print
        User foundUser = users.stream()
                .filter(user -> user.getUserName().equals(username))
                .findFirst()
                .orElseThrow(() -> new InvalidUsernameException("User not found."));
        System.out.println("Found user in DAO: " + foundUser.getUserName()); // Debug print
        return foundUser;
    }

    private User findUserByUsernameInternal(String username) {
        return users.stream()
                .filter(user -> user.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }

    public void updateUser(User user) throws InvalidUsernameException {
        boolean updated = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(user.getUserName())) {
                users.set(i, user);
                updated = true;
                System.out.println("User updated successfully.");
                break;
            }
        }
        if (!updated) {
            throw new InvalidUsernameException("User not found.");
        }
    }

    public void deleteUser(String username) throws InvalidUsernameException {
        boolean removed = users.removeIf(user -> user.getUserName().equals(username));
        if (!removed) {
            throw new InvalidUsernameException("User not found.");
        }
        System.out.println("User deleted successfully.");
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
