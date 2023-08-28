package com.vavelin.example.hexagon.shared.exceptions;

/**
 * When the requested resource cannot be found.
 */
public class ResourceNotFoundException extends ApplicationRuntimeException {
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
