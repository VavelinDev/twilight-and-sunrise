package com.vavelin.example.hexagon;

import com.vavelin.example.hexagon.shared.cqrs.command.usecase.AbstractCommandHandler;
import com.vavelin.example.hexagon.shared.cqrs.command.usecase.Command;
import com.vavelin.example.hexagon.shared.cqrs.command.usecase.CommandBus;
import com.vavelin.example.hexagon.shared.cqrs.command.usecase.SyncCommandBus;
import java.util.Collection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

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

        @Bean
        public MethodValidationPostProcessor getMethodValidationPostProcessor() {
            var processor = new MethodValidationPostProcessor();
            processor.setValidator(this.validator());
            return processor;
        }

        @Bean
        public LocalValidatorFactoryBean validator() {
            return new LocalValidatorFactoryBean();
        }

    }

}
