package com.vavelin.twilight.shop.cart.command.usecase;

import com.vavelin.twilight.shop.cart.command.domain.Cart;
import java.util.Optional;

public interface CartRepositoryPort {
    Optional<Cart> getActiveCart(String username);

    void saveCart(Cart cart);

}
