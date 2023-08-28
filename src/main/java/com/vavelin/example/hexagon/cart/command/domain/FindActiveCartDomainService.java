package com.vavelin.example.hexagon.cart.command.domain;

import com.vavelin.shared.stereotypes.DomainService;

@DomainService
public class FindActiveCartDomainService {
    public Cart find(Long cartId) {
        return new Cart();
    }

}
