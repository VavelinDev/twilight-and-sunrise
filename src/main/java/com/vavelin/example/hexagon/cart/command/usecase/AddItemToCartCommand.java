package com.vavelin.example.hexagon.cart.command.usecase;

import com.vavelin.example.hexagon.shared.cqrs.command.Command;

public record AddItemToCartCommand(
    Long cartId,
    Long productId
    
) implements Command {
}