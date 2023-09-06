package com.vavelin.example.hexagon.cart.command.domain;

import com.vavelin.example.hexagon.shared.ddd.DomainPolicy;
import com.vavelin.example.hexagon.spring.stereotypes.Factory;

@Factory
public class CartFactory {

    private final DomainPolicy<Cart, CartPrice> cartPricePolicy;

    public CartFactory(DomainPolicy<Cart, CartPrice> cartPricePolicy) {
        this.cartPricePolicy = cartPricePolicy;
    }

    public Cart newCart(String username) {
        return newCart(1L, username);
    }

    public Cart newCart(Long id, String username) {
        return new Cart(id, username, cartPricePolicy);
    }

}
