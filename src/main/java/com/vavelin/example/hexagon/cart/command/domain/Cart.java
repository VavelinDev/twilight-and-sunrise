package com.vavelin.example.hexagon.cart.command.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Aggregate Root.
 */
public class Cart {

    private final Long id;

    private boolean active = true;

    private final Set<CartItem> items = new HashSet<>();

    public Cart(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void addItem(CartItem item) {
        items.add(item);
    }

    public boolean isActive() {
        return active;
    }

    public record CartItem(
        Long productId

    ) {
    }
}
