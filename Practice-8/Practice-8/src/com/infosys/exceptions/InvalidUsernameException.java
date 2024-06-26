package com.infosys.exceptions;

/**
 * Exception thrown when a username is invalid.
 * This can occur during user registration or login if the username does not meet validation criteria.
 */
public class InvalidUsernameException extends Exception {
    public InvalidUsernameException(String message) {
        super(message);
    }
}
