package com.vavelin.example.hexagon.cart.command.domain;

import com.vavelin.example.hexagon.shared.exceptions.ResourceNotFoundException;
import com.vavelin.example.hexagon.spring.stereotypes.DomainService;

@DomainService
public class GetCartDomainService {

    private final GetCartStorePort getCartStorePort;

    public GetCartDomainService(GetCartStorePort getCartStorePort) {
        this.getCartStorePort = getCartStorePort;
    }

    public Cart get(Long cartId) {
        Cart cart = getCartStorePort.get(cartId);
        if (!cart.isActive()) {
            throw new ResourceNotFoundException("Cart is inactive. Cart id=" + cart.getId());
        }
        return cart;
    }

}
