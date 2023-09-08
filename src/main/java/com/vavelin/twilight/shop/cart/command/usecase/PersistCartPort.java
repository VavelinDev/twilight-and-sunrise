package com.vavelin.twilight.shop.cart.command.usecase;

import com.vavelin.twilight.shop.cart.command.domain.Cart;
import java.util.function.Consumer;

@FunctionalInterface
public interface PersistCartPort extends Consumer<Cart> {
    void accept(Cart cart);
}
