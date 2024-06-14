package com.infosys.exceptions;

/**
 * Exception thrown when a task cannot be found.
 * This can occur when attempting to update, delete, or mark a task as completed using an invalid task ID.
 */
public class TaskNotFoundException extends Exception {
    public TaskNotFoundException(String message) {
        super(message);
    }
}
