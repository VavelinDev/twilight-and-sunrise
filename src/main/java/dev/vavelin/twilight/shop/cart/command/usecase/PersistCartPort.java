package dev.vavelin.twilight.shop.cart.command.usecase;

import dev.vavelin.twilight.shop.cart.command.domain.Cart;
import java.util.function.Consumer;

@FunctionalInterface
public interface PersistCartPort extends Consumer<Cart> {
    void accept(Cart cart);
}
