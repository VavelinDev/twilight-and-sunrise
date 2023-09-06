package com.vavelin.twilight.shop.shared.exceptions;

/**
 * The most base class for all application's exceptions.
 */
public class ApplicationRuntimeException extends RuntimeException {
    public ApplicationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationRuntimeException(String message) {
        super(message);
    }
}
