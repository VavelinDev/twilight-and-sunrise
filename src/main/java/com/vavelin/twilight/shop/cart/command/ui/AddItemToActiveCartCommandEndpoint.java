package com.vavelin.twilight.shop.cart.command.ui;

import com.vavelin.twilight.shop.cart.command.usecase.AddItemToActiveCartCommand;
import com.vavelin.twilight.shop.shared.cqrs.command.CommandBus;
import com.vavelin.twilight.shop.spring.stereotypes.InputAdapter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@InputAdapter
@RestController
@Validated
@RequestMapping(AddItemToActiveCartCommandEndpoint.PATH)
class AddItemToActiveCartCommandEndpoint {
    public static final String PATH = "/carts/active";

    private final CommandBus commandBus;

    @Autowired
    AddItemToActiveCartCommandEndpoint(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PutMapping
    void handle(@Valid @RequestBody
                AddItemToActiveCartCommandPayload payload,
                @AuthenticationPrincipal
                UserDetails userDetails) {
        var username = userDetails.getUsername();
        var cartCommand = payload.toCommand(username);
        commandBus.dispatch(cartCommand);
    }

    record AddItemToActiveCartCommandPayload(
            @NotNull
            Long productId,
            @NotNull
            @Positive
            Integer quantity
    ) {
        AddItemToActiveCartCommand toCommand(String username) {
            return new AddItemToActiveCartCommand(username, productId, quantity);
        }
    }

}
