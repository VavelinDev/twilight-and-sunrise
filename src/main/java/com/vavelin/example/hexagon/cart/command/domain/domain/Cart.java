package com.vavelin.example.hexagon.cart.command.domain.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Aggregate Root.
 */
public class Cart {

    private final Set<CartItem> items = new HashSet<>();

    public void addItem(CartItem item) {
        items.add(item);
    }

    public record CartItem(
        Long productId

    ) {
    }
}
