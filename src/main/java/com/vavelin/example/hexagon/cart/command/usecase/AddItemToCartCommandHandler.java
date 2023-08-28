package com.vavelin.example.hexagon.cart.command.usecase;

import com.vavelin.example.hexagon.cart.command.domain.Cart;
import com.vavelin.example.hexagon.cart.command.domain.GetCartDomainService;
import com.vavelin.example.hexagon.shared.cqrs.command.usecase.AbstractCommandHandler;
import com.vavelin.example.hexagon.shared.stereotypes.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;

@CommandHandler
public class AddItemToCartCommandHandler extends AbstractCommandHandler<AddItemToCartCommand> {

    private final GetCartDomainService getCartDomainService;

    @Autowired
    public AddItemToCartCommandHandler(GetCartDomainService getCartDomainService) {
        this.getCartDomainService = getCartDomainService;
    }

    @Override
    protected void handle(AddItemToCartCommand addItemToCartCommand) {
        Long cartId = addItemToCartCommand.cartId();
        Long productId = addItemToCartCommand.productId();

        Cart cart = getCartDomainService.get(cartId);
        Cart.CartItem cartItem = new Cart.CartItem(productId);
        cart.addItem(cartItem);
    }
}
