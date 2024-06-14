package com.infosys.exceptions;

/**
 * Exception thrown when attempting to register a user that already exists.
 * This can occur if the username is already taken during the registration process.
 */
public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
