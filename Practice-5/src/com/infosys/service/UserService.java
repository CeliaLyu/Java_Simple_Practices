package com.infosys.service;

import com.infosys.dao.UserDAO;
import com.infosys.pojo.User;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void registerUser(String firstName, String lastName, int role, String password, String username) {
        if (userDAO.findUserByUsername(username) != null) {
            System.out.println("Registration failed: Username already exists.");
            return;
        }
        User newUser = new User(firstName, lastName, role, password, username);
        userDAO.addUser(newUser);
        System.out.println("User registered successfully!");
    }

    public User loginUser(String username, String password) {
        User user = userDAO.findUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("Login successful!");
            return user;
        } else {
            System.out.println("Login failed: Invalid username or password.");
            return null;
        }
    }

    public void updateUser(String username, String newFirstName, String newLastName, int newRole, String newPassword) {
        User user = userDAO.findUserByUsername(username);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        user.setRole(newRole);
        user.setPassword(newPassword);
        if (userDAO.updateUser(user)) {
            System.out.println("User updated successfully!");
        } else {
            System.out.println("Update failed.");
        }
    }

    public void deleteUser(String username) {
        if (userDAO.deleteUser(username)) {
            System.out.println("User deleted successfully!");
        } else {
            System.out.println("Deletion failed: User not found.");
        }
    }
}
