package com.gklyphon.ToDo.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * Represents the structure of error responses sent to the client.
 * <p>
 * This class encapsulates error-related information such as the message, HTTP status,
 * and timestamp of when the error occurred.
 * </p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 28-Oct-2024
 */
@Data
public class ErrorResponse {

    /** The error message describing the issue. */
    private String message;

    /** The HTTP status code representing the error. */
    private int status;

    /** The timestamp indicating when the error occurred. */
    private LocalDateTime timestamp;

    /**
     * Constructs an {@code ErrorResponse} with a message.
     * <p>The status is not specified, and the current timestamp is used.</p>
     *
     * @param message the error message to be sent in the response
     */
    public ErrorResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Constructs an {@code ErrorResponse} with a message and status code.
     * <p>The current timestamp is used.</p>
     *
     * @param message the error message to be sent in the response
     * @param status  the HTTP status code representing the error
     */
    public ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }


    /**
     * Constructs an {@code ErrorResponse} with a message and {@link HttpStatus}.
     * <p>The status code is extracted from the {@code HttpStatus}, and the current timestamp is used.</p>
     *
     * @param message the error message to be sent in the response
     * @param status  the {@code HttpStatus} representing the error
     */
    public ErrorResponse(String message, HttpStatus status) {
        this.message = message;
        this.status = status.value();
        this.timestamp = LocalDateTime.now();
    }


}
