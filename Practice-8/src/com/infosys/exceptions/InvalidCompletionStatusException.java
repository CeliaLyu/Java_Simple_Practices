package com.infosys.exceptions;

/**
 * Exception thrown when a visitor enters an invalid option for viewing tasks by completion status.
 */
public class InvalidCompletionStatusException extends Exception {
    public InvalidCompletionStatusException(String message) {
        super(message);
    }
}
