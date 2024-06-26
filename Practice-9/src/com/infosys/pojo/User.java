package com.infosys.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user with properties to manage identity and book collections.
 */
public class User {
    private String userName; // The user's username
    private int userId; // The user's unique identifier
    private String emailId; // The user's email address
    private String password; // The user's account password
    private List<Book> newBooks; // List of new books associated with the user
    private List<Book> favouriteBooks; // List of favourite books associated with the user
    private List<Book> completedBooks; // List of books the user has completed reading

    /**
     * Constructs a new User with specified details and initializes book lists.
     * @param userName the username for the user
     * @param userId the unique identifier for the user
     * @param emailId the email address of the user
     * @param password the password for the user's account
     */
    public User(String userName, int userId, String emailId, String password) {
        this.userName = userName;
        this.userId = userId;
        this.emailId = emailId;
        this.password = password;
        this.newBooks = new ArrayList<>(); // Initialize list of new books
        this.favouriteBooks = new ArrayList<>(); // Initialize list of favourite books
        this.completedBooks = new ArrayList<>(); // Initialize list of completed books
    }

    // Getter and setter methods

    /**
     * Gets the user's username.
     * @return The username of the user.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the user's username.
     * @param userName The new username for the user.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets the user's unique identifier.
     * @return The user's ID.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets the user's unique identifier.
     * @param userId The new ID for the user.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets the user's email address.
     * @return The email address of the user.
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * Sets the user's email address.
     * @param emailId The new email address for the user.
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * Gets the user's password.
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the user's password.
     * @param password The new password for the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the list of new books associated with the user.
     * @return A list of new books.
     */
    public List<Book> getNewBooks() {
        return newBooks;
    }

    /**
     * Sets the list of new books for the user.
     * @param newBooks The new list of books to be set as new.
     */
    public void setNewBooks(List<Book> newBooks) {
        this.newBooks = newBooks;
    }

    /**
     * Gets the list of favourite books associated with the user.
     * @return A list of favourite books.
     */
    public List<Book> getFavouriteBooks() {
        return favouriteBooks;
    }

    /**
     * Sets the list of favourite books for the user.
     * @param favouriteBooks The new list of books to be set as favourite.
     */
    public void setFavouriteBooks(List<Book> favouriteBooks) {
        this.favouriteBooks = favouriteBooks;
    }

    /**
     * Gets the list of books the user has completed reading.
     * @return A list of completed books.
     */
    public List<Book> getCompletedBooks() {
        return completedBooks;
    }

    /**
     * Sets the list of completed books for the user.
     * @param completedBooks The new list of books to be set as completed.
     */
    public void setCompletedBooks(List<Book> completedBooks) {
        this.completedBooks = completedBooks;
    }
}
