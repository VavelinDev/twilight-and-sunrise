package dev.vavelin.sunrise.shop.cart.command.domain;

import dev.vavelin.framework.spring.stereotypes.AggregateFactory;

@AggregateFactory
public class CartFactory {
    public Cart newCart(String username) {
        // SHOWCASE: simplified ID generation — in production, use a sequence or UUID
        return newCart(1L, username);
    }

    public Cart newCart(Long id, String username) {
        return new Cart(id, username);
    }

}
