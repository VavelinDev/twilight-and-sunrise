package dev.vavelin.framework.shared.exceptions;

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
