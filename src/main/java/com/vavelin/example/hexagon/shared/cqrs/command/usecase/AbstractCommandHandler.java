package com.vavelin.example.hexagon.shared.cqrs.command.usecase;

import com.vavelin.example.hexagon.shared.exceptions.ApplicationRuntimeException;
import java.lang.reflect.ParameterizedType;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractCommandHandler<T extends Command> implements Consumer<T> {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Class<T> commandType;

    public AbstractCommandHandler() {
        commandType = (Class<T>)
            ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    @Override
    public void accept(T t) {
        logger.debug("Handling command [begin]: {}", t);
        try {
            handle(t);
            logger.debug("Handling command [finish]: {}", t);
        } catch (RuntimeException e) {
            throw new ApplicationRuntimeException("Handling command [failure]: " + t, e);
        }
    }

    protected abstract void handle(T t);

    public Class<T> getCommandType() {
        return commandType;
    }
}
