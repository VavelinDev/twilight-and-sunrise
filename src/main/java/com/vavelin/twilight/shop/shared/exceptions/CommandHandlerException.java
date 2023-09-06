package com.vavelin.twilight.shop.shared.exceptions;

/**
 * Thrown by the Command Handlers.
 */
public class CommandHandlerException extends ApplicationRuntimeException {
    public CommandHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandHandlerException(String message) {
        super(message);
    }
}
