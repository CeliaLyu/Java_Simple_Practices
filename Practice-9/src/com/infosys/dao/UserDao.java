package com.infosys.dao;

import com.infosys.pojo.User;
import java.util.HashMap;
import java.util.Map;

public class UserDao {
    private Map<String, User> users = new HashMap<>(); // Stores user objects with their usernames as keys

    /**
     * Adds a user to the users map. The username is used as the key.
     * @param user The user object to add to the map.
     */
    public void addUser(User user) {
        users.put(user.getUserName(), user); // Put the user object into the map with the username as the key
    }

    /**
     * Retrieves a user by username.
     * @param userName The username of the user to retrieve.
     * @return The user object if found, or null if no user with that username exists.
     */
    public User getUser(String userName) {
        return users.get(userName); // Return the user object associated with the username, or null if not found
    }

    /**
     * Checks if a user exists in the users map.
     * @param userName The username to check in the map.
     * @return true if the username exists in the map, false otherwise.
     */
    public boolean userExists(String userName) {
        return users.containsKey(userName); // Check if the username exists in the map and return the result
    }
}
