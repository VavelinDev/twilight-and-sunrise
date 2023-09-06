package com.vavelin.example.hexagon.cart.command.domain;

import com.vavelin.example.hexagon.shared.ddd.DomainPolicy;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Aggregate Root.
 * <p>
 * The Cart is immutable.
 */
public final class Cart {

    private final Long id;

    private final String username;

    private boolean active = true;

    private final Map<Long, CartItem> cartItems;

    private final DomainPolicy<Cart, CartPrice> cartPricePolicy;

    Cart(long id, String username, DomainPolicy<Cart, CartPrice> cartPricePolicy) {
        this(id, username, Collections.emptySet(), cartPricePolicy);
    }

    Cart(long id,
         String username,
         Collection<CartItem> cartItems,
         DomainPolicy<Cart, CartPrice> cartPricePolicy) {
        this.id = id;
        this.username = username;
        this.cartPricePolicy = cartPricePolicy;
        this.cartItems = Map.copyOf(cartItems.stream()
            .collect(Collectors.toMap(CartItem::productId, Function.identity()))
        );
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Cart addProductToCart(Long productId, int quantity) {
        Map<Long, CartItem> newCartItems = new HashMap<>(cartItems.size());
        for (Map.Entry<Long, CartItem> cartItemEntry : cartItems.entrySet()) {
            if (cartItemEntry.getKey().equals(productId)) {
                newCartItems.put(productId, cartItemEntry.getValue().addQuantity(quantity));
            } else {
                newCartItems.put(cartItemEntry.getKey(), cartItemEntry.getValue());
            }
        }
        return new Cart(id, username, newCartItems.values(), cartPricePolicy);
    }

    public Optional<CartItem> getItem(Long productId) {
        return Optional.ofNullable(cartItems.get(productId));
    }

    public Set<CartItem> getCartItems() {
        return Set.copyOf(cartItems.values());
    }

    public boolean isActive() {
        return active;
    }

    public record CartItem(
        Long productId,
        int quantity
    ) {

        CartItem addQuantity(int quantity) {
            return new CartItem(productId, quantity + this.quantity);
        }

    }

}
