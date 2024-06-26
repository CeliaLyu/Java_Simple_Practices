package com.infosys.pojo;

public class User {
    private String firstName;
    private String lastName;
    private int role;  // Use constants for role to improve code readability
    private String password;
    private String userName;

    // Constants for roles
    public static final int ROLE_CLIENT = 1;
    public static final int ROLE_VISITOR = 2;

    public User() {
        // Default constructor if needed for frameworks or certain operations
    }

    public User(String firstName, String lastName, int role, String password, String userName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.password = password;
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + (role == ROLE_CLIENT ? "Client" : "Visitor") +
                ", userName='" + userName + '\'' +
                '}';
    }
}
