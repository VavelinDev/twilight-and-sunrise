package com.vavelin.example.hexagon.cart.command.domain;

import com.vavelin.example.hexagon.shared.domain.vo.Price;
import java.util.Map;

/**
 * The calculated price for a given cart.
 *
 * @param priceByProduct price list required to initialize the prices in the cart
 */
public record CartPrice(Map<Cart.CartItem, Price> priceByProduct) {
    public CartPrice {
        priceByProduct = Map.copyOf(priceByProduct);
    }

    public Price getPriceOfCartItem(Cart.CartItem cartItem) {
        return priceByProduct.get(cartItem);
    }

    public Price calculateTotalPrice() {
        return priceByProduct.values().stream().reduce(Price.ZERO, Price::add);
    }

}
