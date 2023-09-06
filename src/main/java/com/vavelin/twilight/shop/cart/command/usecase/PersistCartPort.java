package com.vavelin.twilight.shop.cart.command.usecase;

import com.vavelin.twilight.shop.cart.command.domain.Cart;

public interface PersistCartPort {
    void saveCart(Cart cart);
}
