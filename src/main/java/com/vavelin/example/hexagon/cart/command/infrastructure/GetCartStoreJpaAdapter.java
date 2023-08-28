package com.vavelin.example.hexagon.cart.command.infrastructure;

import com.vavelin.example.hexagon.cart.command.domain.Cart;
import com.vavelin.example.hexagon.cart.command.domain.GetCartStorePort;
import org.springframework.stereotype.Component;

@Component
public class GetCartStoreJpaAdapter implements GetCartStorePort {
    @Override
    public Cart get(Long cartId) {
        return new Cart(cartId);
    }
}
