package com.vavelin.example.hexagon.cart.command.domain;

import com.vavelin.example.hexagon.spring.stereotypes.Factory;

@Factory
public class CartFactory {
    public Cart newCart(String username) {
        return newCart(1L, username);
    }

    public Cart newCart(Long id, String username) {
        return new Cart(id, username);
    }

}
