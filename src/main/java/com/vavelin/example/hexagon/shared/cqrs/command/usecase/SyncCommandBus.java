package com.vavelin.example.hexagon.shared.cqrs.command.usecase;

import static java.util.Objects.requireNonNull;

import com.vavelin.example.hexagon.shared.exceptions.InternalErrorException;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncCommandBus implements CommandBus {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Map<Class<Command>, AbstractCommandHandler<Command>> handlerRegistry;

    public SyncCommandBus(Collection<AbstractCommandHandler<Command>> commandHandlers) {
        if (commandHandlers.isEmpty()) {
            throw new IllegalStateException(
                "No Command Handlers detected. Please register at least one handler.");
        }

        logger.info("SyncCommandBus. Commands Registered: {}", commandHandlers.size());

        this.handlerRegistry =
            commandHandlers.stream()
                .collect(Collectors.toMap(AbstractCommandHandler::getCommandType, it -> it));
    }

    @Override
    public <T extends Command> void dispatch(T command) {
        requireNonNull(command, "command cannot be null");

        final var commandType = command.getClass();

        if (!handlerRegistry.containsKey(commandType)) {
            throw new InternalErrorException("Unknown command: " + commandType);
        }

        handlerRegistry.get(commandType).handle(command);
    }
}
