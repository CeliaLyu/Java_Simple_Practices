package com.infosys.exceptions;

/**
 * Exception thrown when the maximum number of tasks has been reached.
 * This can occur if there is a limit on the number of tasks that can be created.
 */
public class MaxTasksReachedException extends Exception {
    public MaxTasksReachedException(String message) {
        super(message);
    }
}
