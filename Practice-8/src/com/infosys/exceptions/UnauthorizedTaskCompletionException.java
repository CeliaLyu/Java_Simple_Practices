package com.infosys.exceptions;

/**
 * Exception thrown when a visitor tries to mark a task as completed that is not assigned to them.
 */
public class UnauthorizedTaskCompletionException extends Exception {
    public UnauthorizedTaskCompletionException(String message) {
        super(message);
    }
}
