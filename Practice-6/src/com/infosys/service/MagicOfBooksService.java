package com.infosys.service;

import com.infosys.dao.UserDao;
import com.infosys.dao.BookDao;
import com.infosys.pojo.Book;
import com.infosys.pojo.User;
import java.util.List;

public class MagicOfBooksService {
    private UserDao userDao = new UserDao(); // Access layer for user data
    private BookDao bookDao = new BookDao(); // Access layer for book data
    private User currentUser; // Currently logged-in user

    /**
     * Registers a new user with the given username, password, and email.
     * If the username already exists, returns false to indicate failure.
     * @param userName the username of the new user
     * @param password the password of the new user
     * @param emailId the email address of the new user
     * @return true if registration is successful, false otherwise
     */
    public boolean registerUser(String userName, String password, String emailId) {
        if (userDao.userExists(userName)) { // Check if the user already exists
            return false;
        }
        User newUser = new User(userName, userName.hashCode(), emailId, password);
        userDao.addUser(newUser); // Add new user to the database
        return true;
    }

    /**
     * Attempts to log a user in with the provided username and password.
     * Sets currentUser if successful.
     * @param userName the username of the user attempting to log in
     * @param password the password of the user
     * @return true if login is successful, false otherwise
     */
    public boolean loginUser(String userName, String password) {
        User user = userDao.getUser(userName); // Retrieve user from the database
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user; // Set currentUser on successful login
            return true;
        }
        return false;
    }

    /**
     * Logs out the current user by setting currentUser to null.
     */
    public void logoutUser() {
        currentUser = null;
    }

    /**
     * Returns the currently logged-in user.
     * @return the current user or null if no user is logged in
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Retrieves the next available book ID from the BookDao.
     * @return the next book ID
     */
    public int getNextBookId() {
        return bookDao.getNextId();
    }

    /**
     * Adds a book to the system if it does not already exist.
     * @param book the book to add
     * @return true if the book was added, false if the book already exists
     */
    public boolean addBook(Book book) {
        if (bookDao.getBook(book.getBookId()) != null) { // Check if the book already exists
            return false;
        }
        bookDao.addBook(book); // Add new book to the database
        return true;
    }

    /**
     * Retrieves a book by its ID.
     * @param bookId the ID of the book to retrieve
     * @return the book if found, null otherwise
     */
    public Book getBook(int bookId) {
        return bookDao.getBook(bookId);
    }

    /**
     * Retrieves all books from the database.
     * @return a list of all books
     */
    public List<Book> getAllBooks() {
        return bookDao.getAllBooks();
    }

    /**
     * Updates the information of an existing book.
     * @param book the book with updated information
     */
    public void updateBook(Book book) {
        bookDao.updateBook(book.getBookId(), book);
    }

    /**
     * Deletes a book by its ID.
     * @param bookId the ID of the book to delete
     * @return true if the book was deleted, false if the book was not found
     */
    public boolean deleteBook(int bookId) {
        return bookDao.deleteBook(bookId);
    }

    /**
     * Marks a book with a specified status, e.g., new, favorite, or completed.
     * @param bookId the ID of the book to mark
     * @param markType the type of mark (1 for new, 2 for favorite, 3 for completed)
     * @return true if the operation is successful, false if the book does not exist
     */
    public boolean markBook(int bookId, int markType) {
        Book book = bookDao.getBook(bookId);
        if (book == null) {
            return false;
        }
        switch (markType) {
            case 1: currentUser.getNewBooks().add(book); break;
            case 2: currentUser.getFavouriteBooks().add(book); break;
            case 3: currentUser.getCompletedBooks().add(book); break;
            default: return false;
        }
        return true;
    }
}
