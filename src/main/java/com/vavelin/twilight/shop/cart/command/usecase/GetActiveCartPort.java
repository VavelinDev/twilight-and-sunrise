package com.vavelin.twilight.shop.cart.command.usecase;

import com.vavelin.twilight.shop.cart.command.domain.Cart;
import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
public interface GetActiveCartPort extends Function<String, Optional<Cart>> {
    Optional<Cart> apply(String username);

}
