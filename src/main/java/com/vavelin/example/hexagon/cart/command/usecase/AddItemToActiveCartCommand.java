package com.vavelin.example.hexagon.cart.command.usecase;

import com.vavelin.example.hexagon.shared.cqrs.command.Command;

public record AddItemToActiveCartCommand(
    String username,
    Long productId,
    int quantity

) implements Command {
}