package com.infosys.exceptions;

/**
 * Exception thrown when a task description is invalid.
 * This can occur if the description does not meet specific criteria such as length or content restrictions.
 */
public class InvalidTaskDescriptionException extends Exception {
    public InvalidTaskDescriptionException(String message) {
        super(message);
    }
}
