package com.infosys.pojo;

/**
 * Represents a book with a name, author, description, and an identifier.
 */
public class Book {
    private String bookName; // The name of the book
    private String authorName; // The name of the author of the book
    private String description; // A brief description of the book
    private int bookId; // Unique identifier for the book

    /**
     * Constructs a Book object with specified details.
     * @param bookName The name of the book.
     * @param authorName The author's name.
     * @param description A short description of the book.
     * @param bookId The unique identifier for the book.
     */
    public Book(String bookName, String authorName, String description, int bookId) {
        this.bookName = bookName;
        this.authorName = authorName;
        this.description = description;
        this.bookId = bookId;
    }

    // Getters and setters for accessing and updating the properties of the book.

    /**
     * Gets the name of the book.
     * @return The name of the book.
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * Sets the name of the book.
     * @param bookName The new name for the book.
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * Gets the author's name of the book.
     * @return The author's name of the book.
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * Sets the author's name of the book.
     * @param authorName The new author's name for the book.
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * Gets the description of the book.
     * @return The description of the book.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the book.
     * @param description The new description for the book.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the unique identifier of the book.
     * @return The unique identifier of the book.
     */
    public int getBookId() {
        return bookId;
    }

    /**
     * Sets the unique identifier of the book.
     * @param bookId The new identifier for the book.
     */
    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
