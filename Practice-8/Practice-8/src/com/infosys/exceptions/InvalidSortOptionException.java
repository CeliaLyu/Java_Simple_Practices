package com.infosys.exceptions;

/**
 * Exception thrown when the user enters an invalid option for sorting tasks.
 */
public class InvalidSortOptionException extends Exception {
    public InvalidSortOptionException(String message) {
        super(message);
    }
}
