package com.vavelin.example.hexagon;

import com.vavelin.shared.cqrs.command.AbstractCommandHandler;
import com.vavelin.shared.cqrs.command.Command;
import com.vavelin.shared.cqrs.command.CommandBus;
import com.vavelin.shared.cqrs.command.SyncCommandBus;
import java.util.Collection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class HexagonApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonApplication.class, args);
    }


    @Configuration
    public static class BeanConfiguration {

        @Bean
        public CommandBus commandBus(
            Collection<? extends AbstractCommandHandler<? extends Command>> commandHandlers) {
            return new SyncCommandBus(
                (Collection<AbstractCommandHandler<Command>>) commandHandlers);
        }

    }

}
