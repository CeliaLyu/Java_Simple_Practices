package com.infosys.exceptions;

/**
 * Exception thrown when a task title is invalid.
 * This can occur if the title does not meet specific criteria such as length or character restrictions.
 */
public class InvalidTaskTitleException extends Exception {
    public InvalidTaskTitleException(String message) {
        super(message);
    }
}
