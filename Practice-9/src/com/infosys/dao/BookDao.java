package com.infosys.dao;

import com.infosys.pojo.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class BookDao {
    private Map<Integer, Book> books = new HashMap<>();  // A map to hold books with their IDs as keys
    private int nextId = 1;  // A variable to keep track of the next ID to be assigned

    /**
     * Retrieves a book by its ID.
     * @param bookId The ID of the book to retrieve.
     * @return The book if it exists in the map, otherwise null.
     */
    public Book getBook(int bookId) {
        return books.get(bookId);
    }

    /**
     * Adds a book to the map with an auto-incremented ID.
     * Sets the book's ID to the current value of nextId, then increments nextId.
     * @param book The book to add to the map.
     */
    public void addBook(Book book) {
        book.setBookId(nextId);  // Set the book ID before putting it in the map
        books.put(nextId++, book);  // Put the book in the map and increment the ID
    }

    /**
     * Retrieves all books in the map as a List.
     * @return A new ArrayList containing all the books in the map.
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());  // Convert book values to a list
    }

    /**
     * Updates an existing book's details in the map.
     * Only updates if the book ID already exists in the map.
     * @param bookId The ID of the book to update.
     * @param book The new book object to replace the existing one.
     */
    public void updateBook(int bookId, Book book) {
        if (books.containsKey(bookId)) {  // Check if the book ID exists
            books.put(bookId, book);  // Replace the existing book with the new book
        }
    }

    /**
     * Deletes a book from the map by its ID.
     * @param bookId The ID of the book to delete.
     * @return true if the book was successfully deleted, false if the book was not found.
     */
    public boolean deleteBook(int bookId) {
        if (books.containsKey(bookId)) {  // Check if the book ID exists
            books.remove(bookId);  // Remove the book from the map
            return true;  // Return true indicating successful deletion
        }
        return false;  // Return false indicating the book was not found
    }

    /**
     * Gets the next valid ID that can be assigned to a new book.
     * @return The next ID to be used for a new book.
     */
    public int getNextId() {
        return nextId;  // Return the next ID that will be assigned
    }
}
