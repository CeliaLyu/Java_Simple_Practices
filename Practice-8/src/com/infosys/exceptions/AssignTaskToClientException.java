package com.infosys.exceptions;

/**
 * Exception thrown when a client tries to assign a task to another client.
 */
public class AssignTaskToClientException extends Exception {
    public AssignTaskToClientException(String message) {
        super(message);
    }
}
