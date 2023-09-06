package com.vavelin.example.hexagon.cart.command.usecase;

import com.vavelin.example.hexagon.cart.command.domain.Cart;
import com.vavelin.example.hexagon.cart.command.domain.CartFactory;
import com.vavelin.example.hexagon.shared.cqrs.command.CommandHandler;
import com.vavelin.example.hexagon.spring.stereotypes.CommandHandlerService;
import org.springframework.beans.factory.annotation.Autowired;

@CommandHandlerService
public class AddItemToActiveCartCommandHandler implements
    CommandHandler<AddItemToActiveCartCommand> {

    private final CartRepositoryPort cartRepository;
    private final CartFactory cartFactory;

    @Autowired
    public AddItemToActiveCartCommandHandler(CartRepositoryPort cartRepository,
                                             CartFactory cartFactory) {
        this.cartRepository = cartRepository;
        this.cartFactory = cartFactory;
    }

    @Override
    public void handle(AddItemToActiveCartCommand command) {
        final String username = command.username();
        final Long productId = command.productId();
        final int quantity = command.quantity();

        final Cart loadedCart =
            cartRepository.getActiveCart(username).orElseGet(() -> cartFactory.newCart(username));

        final Cart updatedCart = loadedCart.addProductToCart(productId, quantity);

        cartRepository.saveCart(updatedCart);
    }
}
