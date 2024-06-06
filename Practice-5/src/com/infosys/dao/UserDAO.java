package com.infosys.dao;

import com.infosys.pojo.User;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private List<User> users = new ArrayList<>();
    private static final int MAX_USERS = 5;

    public boolean addUser(User user) {
        if (users.size() >= MAX_USERS) {
            System.out.println("Cannot add more users. Maximum limit of " + MAX_USERS + " users reached.");
            return false;
        }
        users.add(user);
        System.out.println("User added successfully. Total users: " + users.size());
        return true;
    }

    public User findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUserName().equals(username))
                .findFirst()
                .orElse(null);
    }

    public boolean updateUser(User user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserName().equals(user.getUserName())) {
                users.set(i, user);
                System.out.println("User updated successfully.");
                return true;
            }
        }
        System.out.println("User not found.");
        return false;
    }

    public boolean deleteUser(String username) {
        boolean removed = users.removeIf(user -> user.getUserName().equals(username));
        if (removed) {
            System.out.println("User deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
        return removed;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
