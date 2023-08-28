package com.vavelin.example.hexagon.cart.command.domain;

public interface GetCartStorePort {
    Cart get(Long cartId);
}
