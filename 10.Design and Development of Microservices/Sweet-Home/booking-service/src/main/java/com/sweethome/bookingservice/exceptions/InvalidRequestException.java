package com.sweethome.bookingservice.exceptions;

/**
 * Custom exception to handle invalid requests, gracefully.
 */
public class InvalidRequestException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidRequestException(String message) {
        super(message);
    }
}
