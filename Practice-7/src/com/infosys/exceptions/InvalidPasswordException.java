package com.infosys.exceptions;

/**
 * Exception thrown when a password is invalid.
 * This can occur during user login if the password does not match the stored password for a given username.
 */
public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
