package com.vavelin.shared.exceptions;

/**
 * All the application's internal errors.
 */
public class InternalErrorException extends ApplicationRuntimeException {
    public InternalErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalErrorException(String message) {
        super(message);
    }
}
