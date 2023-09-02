package com.vavelin.example.hexagon.shared.cqrs.command;

/**
 * Interface for all command handlers in the system.
 *
 * @param <T> the type of the handled command
 */
@FunctionalInterface
public interface CommandHandler<T extends Command> {
    void handle(T command);

}
