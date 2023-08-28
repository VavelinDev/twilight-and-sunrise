package com.vavelin.example.hexagon.cart.command.usecase;

import com.vavelin.example.hexagon.cart.command.domain.Cart;
import com.vavelin.example.hexagon.cart.command.domain.FindActiveCartDomainService;
import com.vavelin.shared.cqrs.command.AbstractCommandHandler;
import com.vavelin.shared.stereotypes.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;

@CommandHandler
public class AddItemToCartCommandHandler extends AbstractCommandHandler<AddItemToCartCommand> {

    private final FindActiveCartDomainService findActiveCartDomainService;

    @Autowired
    public AddItemToCartCommandHandler(FindActiveCartDomainService findActiveCartDomainService) {
        this.findActiveCartDomainService = findActiveCartDomainService;
    }

    @Override
    protected void handle(AddItemToCartCommand addItemToCartCommand) {
        Long cartId = addItemToCartCommand.cartId();
        Long productId = addItemToCartCommand.productId();

        Cart cart = findActiveCartDomainService.find(cartId);
        Cart.CartItem cartItem = new Cart.CartItem(productId);
        cart.addItem(cartItem);
    }
}
