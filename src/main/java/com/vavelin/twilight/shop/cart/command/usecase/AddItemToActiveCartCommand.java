package com.vavelin.twilight.shop.cart.command.usecase;

import com.vavelin.twilight.shop.shared.cqrs.command.Command;

public record AddItemToActiveCartCommand(
    String username,
    Long productId,
    int quantity

) implements Command {
}