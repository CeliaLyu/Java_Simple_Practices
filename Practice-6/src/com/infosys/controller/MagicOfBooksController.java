package com.infosys.controller;

import com.infosys.service.MagicOfBooksService;
import com.infosys.pojo.Book;
import java.util.Scanner;

public class MagicOfBooksController {
    private MagicOfBooksService service = new MagicOfBooksService(); // Service layer handling all business logic
    private Scanner scanner = new Scanner(System.in); // Scanner for input from console

    // Main entry point to start the application
    public static void main(String[] args) {
        MagicOfBooksController controller = new MagicOfBooksController();
        controller.startApplication();
    }

    // Main loop that displays the application menu and handles user input
    public void startApplication() {
        boolean running = true;
        while (running) {
            System.out.println("Choose option: 1 for Register, 2 for Login, 3 to Logout, 0 to Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the input buffer to avoid skipping input

            switch (choice) {
                case 1: // User registration
                    registerUser();
                    break;
                case 2: // User login
                    if (loginUser()) {
                        userMenu(); // Access user-specific operations
                    }
                    break;
                case 3: // User logout
                    service.logoutUser();
                    System.out.println("Logged out successfully.");
                    break;
                case 0: // Exit the application
                    System.out.println("Exiting application.");
                    System.exit(0);
                    break;
                default: // Handle incorrect input
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Handles the registration process
    private void registerUser() {
        System.out.println("Enter Username:");
        String username = scanner.nextLine();
        System.out.println("Enter Password:");
        String password = scanner.nextLine();
        System.out.println("Enter Email ID:");
        String emailId = scanner.nextLine();

        // Register user and handle response
        if (service.registerUser(username, password, emailId)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            System.out.println("Registration failed. Username already exists.");
        }
    }

    // Handles the login process
    private boolean loginUser() {
        System.out.println("Enter Username:");
        String username = scanner.nextLine();
        System.out.println("Enter Password:");
        String password = scanner.nextLine();

        // Log in user and handle response
        if (service.loginUser(username, password)) {
            System.out.println("Login successful.");
            return true;
        } else {
            System.out.println("Login failed. Incorrect username or password.");
            return false;
        }
    }

    // Menu for logged-in users
    private void userMenu() {
        boolean logged = true;
        while (logged) {
            System.out.println("User Menu - Choose option: 1 to Add Book, 2 to View Books, 3 to Update Book, 4 to Delete Book, 5 to Mark Book, 6 to Logout, 0 to Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the input buffer

            switch (choice) {
                case 1:
                    addBook(); // Add a new book
                    break;
                case 2:
                    viewBooks(); // View existing books
                    break;
                case 3:
                    updateBook(); // Update book details
                    break;
                case 4:
                    deleteBook(); // Delete a book
                    break;
                case 5:
                    markBook(); // Mark a book's status
                    break;
                case 6: // Log out
                    logged = false;
                    service.logoutUser();
                    System.out.println("Logged out successfully.");
                    break;
                case 0: // Exit the application from within the user menu
                    System.out.println("Exiting application.");
                    System.exit(0);
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Method to add a book with user input
    private void addBook() {
        System.out.println("Enter Book Name:");
        String bookName = scanner.nextLine();
        System.out.println("Enter Author Name:");
        String authorName = scanner.nextLine();
        System.out.println("Enter Description:");
        String description = scanner.nextLine();

        Book book = new Book(bookName, authorName, description, service.getNextBookId());
        if (service.addBook(book)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Failed to add book.");
        }
    }

    // Method to mark a book with a specific status
    private void markBook() {
        System.out.println("Enter Book ID to mark:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Clear the input buffer
        System.out.println("Mark as (1 for New, 2 for Favorite, 3 for Completed):");
        int mark = scanner.nextInt();
        scanner.nextLine(); // Clear the input buffer

        if (service.markBook(bookId, mark)) {
            System.out.println("Book marked successfully.");
        } else {
            System.out.println("Failed to mark book.");
        }
    }

    // View books either by ID or all books
    private void viewBooks() {
        System.out.println("Enter Book ID to view details, or 0 to view all:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Clear the input buffer
        if (bookId == 0) {
            service.getAllBooks().forEach(b -> {
                String status = bookStatus(b);
                System.out.println("ID: " + b.getBookId() + ", Name: " + b.getBookName() + ", Author: " + b.getAuthorName() + ", Description: " + b.getDescription() + ", Status: " + status);
            });
        } else {
            Book book = service.getBook(bookId);
            if (book != null) {
                String status = bookStatus(book);
                System.out.println("Name: " + book.getBookName() + ", Author: " + book.getAuthorName() + ", Description: " + book.getDescription() + ", Status: " + status);
            } else {
                System.out.println("Book not found.");
            }
        }
    }

    // Helper method to determine the status of a book
    private String bookStatus(Book book) {
        if (service.getCurrentUser().getNewBooks().contains(book)) return "New";
        if (service.getCurrentUser().getFavouriteBooks().contains(book)) return "Favorite";
        if (service.getCurrentUser().getCompletedBooks().contains(book)) return "Completed";
        return "Unmarked";
    }

    // Update book details
    private void updateBook() {
        System.out.println("Enter Book ID to update:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Clear the input buffer
        Book existingBook = service.getBook(bookId);
        if (existingBook != null) {
            System.out.println("Enter new Name (current: " + existingBook.getBookName() + "):");
            String newName = scanner.nextLine();
            System.out.println("Enter new Author (current: " + existingBook.getAuthorName() + "):");
            String newAuthor = scanner.nextLine();
            System.out.println("Enter new Description (current: " + existingBook.getDescription() + "):");
            String newDescription = scanner.nextLine();

            Book updatedBook = new Book(newName.isEmpty() ? existingBook.getBookName() : newName,
                    newAuthor.isEmpty() ? existingBook.getAuthorName() : newAuthor,
                    newDescription.isEmpty() ? existingBook.getDescription() : newDescription,
                    bookId);
            service.updateBook(updatedBook);
            System.out.println("Book updated successfully.");
        } else {
            System.out.println("Book not found.");
        }
    }

    // Delete a book by ID
    private void deleteBook() {
        System.out.println("Enter Book ID to delete:");
        int bookId = scanner.nextInt();
        scanner.nextLine(); // Clear the input buffer
        if (service.deleteBook(bookId)) {
            System.out.println("Book deleted successfully.");
        } else {
            System.out.println("Failed to delete book.");
        }
    }
}
