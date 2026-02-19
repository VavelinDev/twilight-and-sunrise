package dev.vavelin.twilight.shop.cart.command.domain;

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

    private final boolean active;

    private final Map<Long, CartItem> cartItems;

    Cart(long id, String username) {
        this(id, username, true, Collections.emptySet());
    }

    Cart(long id,
            String username,
            boolean active,
            Collection<CartItem> cartItems) {
        this.id = id;
        this.username = username;
        this.active = active;
        this.cartItems = Map.copyOf(cartItems.stream()
                .collect(Collectors.toMap(CartItem::productId, Function.identity())));
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Cart addProductToCart(Long productId, int quantity) {
        Map<Long, CartItem> newCartItems = new HashMap<>(cartItems);
        newCartItems.merge(productId, new CartItem(productId, quantity),
                (existing, added) -> existing.addQuantity(added.quantity()));
        return new Cart(id, username, active, newCartItems.values());
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
            int quantity) {

        CartItem addQuantity(int quantity) {
            return new CartItem(productId, quantity + this.quantity);
        }

    }

}
