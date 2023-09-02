package com.vavelin.example.hexagon.cart.command.usecase;

import com.vavelin.example.hexagon.cart.command.domain.Cart;
import com.vavelin.example.hexagon.cart.command.domain.GetCartDomainService;
import com.vavelin.example.hexagon.shared.cqrs.command.CommandHandler;
import com.vavelin.example.hexagon.spring.stereotypes.CommandHandlerService;
import org.springframework.beans.factory.annotation.Autowired;

@CommandHandlerService
public class AddItemToCartCommandHandler implements
    CommandHandler<AddItemToCartCommand> {

    private final GetCartDomainService getCartDomainService;

    @Autowired
    public AddItemToCartCommandHandler(GetCartDomainService getCartDomainService) {
        this.getCartDomainService = getCartDomainService;
    }

    @Override
    public void handle(AddItemToCartCommand command) {
        Long cartId = command.cartId();
        Long productId = command.productId();

        Cart cart = getCartDomainService.get(cartId);
        Cart.CartItem cartItem = new Cart.CartItem(productId);
        cart.addItem(cartItem);
    }
}
