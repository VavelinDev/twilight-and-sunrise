package com.vavelin.shared.cqrs.command;

/**
 * Represents an interface for command bus. Command Bus are responsible for dispatching commands
 * to the appropriate handler.
 */
public interface CommandBus {

    /**
     * Dispatches the given command to the appropriate handler.
     *
     * @param command a command to be dispatched
     * @param <T>     the type of the command
     */
    <T extends Command> void dispatch(T command);
}

