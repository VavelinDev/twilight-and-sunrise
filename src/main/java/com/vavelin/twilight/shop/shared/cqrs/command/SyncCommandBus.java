package com.vavelin.twilight.shop.shared.cqrs.command;

import static java.util.Objects.requireNonNull;

import com.vavelin.twilight.shop.shared.exceptions.CommandHandlerException;
import com.vavelin.twilight.shop.shared.exceptions.InternalErrorException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A trivial example and synchronous implementation of {@link CommandBus}.
 * Contains the collection of the registered Command Handlers.
 * Adds an observability in the form of logs.
 */
public class SyncCommandBus implements CommandBus {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<Type, CommandHandler<Command>> handlerRegistry;

    public SyncCommandBus(Collection<CommandHandler<Command>> commandHandlers) {
        if (commandHandlers.isEmpty()) {
            throw new IllegalStateException(
                "No Command Handlers detected. Please register at least one handler.");
        }

        logger.info("SyncCommandBus. Commands Registered: {}", commandHandlers.size());

        this.handlerRegistry = commandHandlers.stream()
            .peek(this::commandType)
            .collect(Collectors.toMap(this::commandType, it -> it));
    }

    @Override
    public <T extends Command> void dispatch(T command) {
        requireNonNull(command, "command cannot be null");

        final var commandType = command.getClass();

        if (!handlerRegistry.containsKey(commandType)) {
            throw new InternalErrorException("Unknown command: " + commandType);
        }

        try {
            logger.debug("Handling command [begin]: {}", command);
            handlerRegistry.get(commandType).accept(command);
            logger.debug("Handling command [finish]: {}", command);
        } catch (RuntimeException e) {
            throw new CommandHandlerException("Handling command [failure]: " + command, e);
        }
    }

    private <T extends Command> Class<T> commandType(CommandHandler<T> commandHandler) {
        try {
            return (Class<T>) ((ParameterizedType) Arrays.stream(
                    commandHandler.getClass().getGenericInterfaces())
                .filter(it -> ((ParameterizedType) it).getRawType().equals(CommandHandler.class))
                .findFirst().get())
                .getActualTypeArguments()[0];
        } catch (RuntimeException e) {
            throw new InternalErrorException(
                "Makes sure the handler class " + commandHandler.getClass() +
                    " implements directly the " + CommandHandler.class, e);
        }
    }

}
