package dev.vavelin.twilight.shop;

import dev.vavelin.framework.shared.cqrs.command.Command;
import dev.vavelin.framework.shared.cqrs.command.CommandBus;
import dev.vavelin.framework.shared.cqrs.command.CommandHandler;
import dev.vavelin.framework.shared.cqrs.command.SyncCommandBus;
import java.util.Collection;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication(
    exclude = {SecurityAutoConfiguration.class},
    scanBasePackages = {"dev.vavelin.twilight", "dev.vavelin.framework"}
)
public class TwilightShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwilightShopApplication.class, args);
    }

    @Configuration
    @EnableJpaRepositories
    public static class BeanConfiguration {

        @Bean
        public CommandBus commandBus(
            Collection<? extends CommandHandler<? extends Command>> commandHandlers
        ) {
            return new SyncCommandBus((Collection<CommandHandler<Command>>) commandHandlers);
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

    @Configuration
    @EnableWebSecurity
    public static class Security {
        @Bean
        public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
            UserDetails user = User.withUsername("user")
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build();

            UserDetails admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("USER", "ADMIN")
                .build();

            return new InMemoryUserDetailsManager(user, admin);
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http,
                                               AuthenticationManager authenticationManager)
            throws Exception {
            http.csrf(it -> it.disable())
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .authenticationManager(authenticationManager)
                .httpBasic(it -> {
                });
            return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        @Bean
        @Primary
        public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

    }

}
