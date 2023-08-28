package com.vavelin.example.hexagon.cart.command.ui;

import com.vavelin.example.hexagon.cart.command.usecase.AddItemToCartCommand;
import com.vavelin.example.hexagon.shared.cqrs.command.usecase.CommandBus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@RequestMapping("/carts")
public class AddItemToCartCommandEndpoint {

    private final CommandBus commandBus;

    @Autowired
    public AddItemToCartCommandEndpoint(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    @PutMapping("/{cartId}")
    public void handle(
        @PathVariable("cartId") @Positive Long cartId,
        @Valid @RequestBody AddItemToCartCommandPayload payload
    ) {
        var cartCommand = payload.toCommandForCartId(cartId);
        commandBus.dispatch(cartCommand);
    }

    public record AddItemToCartCommandPayload(
        @NotNull
        Long productId
    ) {
        AddItemToCartCommand toCommandForCartId(Long cartId) {
            return new AddItemToCartCommand(cartId, productId);
        }
    }

}
