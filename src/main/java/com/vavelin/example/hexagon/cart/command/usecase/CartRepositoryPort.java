package com.vavelin.example.hexagon.cart.command.usecase;

import com.vavelin.example.hexagon.cart.command.domain.Cart;
import java.util.Optional;

public interface CartRepositoryPort {
    Optional<Cart> getActiveCart(String username);

    void saveCart(Cart cart);

}
