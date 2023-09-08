package com.vavelin.twilight.shop.shared.cqrs.command;

import java.util.function.Consumer;

/**
 * Interface for all command handlers in the system.
 *
 * @param <T> the type of the handled command
 */
@FunctionalInterface
public interface CommandHandler<T extends Command> extends Consumer<T> {
    void accept(T command);

}
