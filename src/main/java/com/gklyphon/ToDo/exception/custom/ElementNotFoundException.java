package com.gklyphon.ToDo.exception.custom;

/**
 * Custom exception to indicate that a specific element was not found.
 * <p>
 * This exception extends {@link RuntimeException} and is intended to be thrown
 * when an operation cannot locate the requested element in the data source.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
public class ElementNotFoundException extends RuntimeException {

    /**
     * Custom exception to indicate that a specific element was not found.
     * <p>
     * This exception extends {@link RuntimeException} and is intended to be thrown
     * when an operation cannot locate the requested element in the data source.
     * </p>
     */
    public ElementNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code ElementNotFoundException} with the specified detail message and cause.
     *
     * @param message the detail message explaining the cause of the exception
     * @param cause   the underlying cause of the exception, typically another exception
     */
    public ElementNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
