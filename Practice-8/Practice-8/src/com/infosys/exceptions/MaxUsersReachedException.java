package com.infosys.exceptions;

/**
 * Exception thrown when the maximum number of users has been reached.
 * This can occur if there is a limit on the number of users that can be created.
 */
public class MaxUsersReachedException extends Exception {
    public MaxUsersReachedException(String message) {
        super(message);
    }
}
