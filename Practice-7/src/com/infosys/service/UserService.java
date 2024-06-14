package com.infosys.service;

import com.infosys.dao.UserDAO;
import com.infosys.pojo.User;
import com.infosys.exceptions.InvalidUsernameException;
import com.infosys.exceptions.InvalidPasswordException;
import com.infosys.exceptions.UserAlreadyExistsException;
import com.infosys.exceptions.MaxUsersReachedException;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public void registerUser(String firstName, String lastName, int role, String password, String username) throws UserAlreadyExistsException, MaxUsersReachedException, InvalidUsernameException {
        User newUser = new User(firstName, lastName, role, password, username);
        userDAO.addUser(newUser);
        System.out.println("User registered successfully!");
    }

    public User loginUser(String username, String password) throws InvalidUsernameException, InvalidPasswordException {
        User user = userDAO.findUserByUsername(username);
        if (user.getPassword().equals(password)) {
            System.out.println("Login successful!");
            return user;
        } else {
            throw new InvalidPasswordException("Invalid password.");
        }
    }

    public void updateUser(String username, String newFirstName, String newLastName, int newRole, String newPassword) throws InvalidUsernameException {
        User user = userDAO.findUserByUsername(username);
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        user.setRole(newRole);
        user.setPassword(newPassword);
        userDAO.updateUser(user);
        System.out.println("User updated successfully!");
    }

    public void deleteUser(String username) throws InvalidUsernameException {
        userDAO.deleteUser(username);
        System.out.println("User deleted successfully!");
    }
}
