package dev.vavelin.twilight.shop.cart.command.usecase;

import dev.vavelin.framework.shared.cqrs.command.Command;

public record AddItemToActiveCartCommand(
    String username,
    Long productId,
    int quantity

) implements Command {
}