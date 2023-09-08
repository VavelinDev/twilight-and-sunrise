package com.vavelin.twilight.shop.cart.command.usecase;

import com.vavelin.twilight.shop.cart.command.domain.Cart;
import com.vavelin.twilight.shop.cart.command.domain.CartFactory;
import com.vavelin.twilight.shop.shared.cqrs.command.CommandHandler;
import com.vavelin.twilight.shop.spring.stereotypes.CommandHandlerService;
import org.springframework.beans.factory.annotation.Autowired;

@CommandHandlerService
class AddItemToActiveCartCommandHandler implements
    CommandHandler<AddItemToActiveCartCommand> {

    private final GetActiveCartPort getActiveCartPort;
    private final PersistCartPort persistCartPort;
    private final CartFactory cartFactory;

    @Autowired
    AddItemToActiveCartCommandHandler(GetActiveCartPort getActiveCartPort,
                                             PersistCartPort persistCartPort,
                                             CartFactory cartFactory) {
        this.getActiveCartPort = getActiveCartPort;
        this.persistCartPort = persistCartPort;
        this.cartFactory = cartFactory;
    }

    @Override
    public void accept(AddItemToActiveCartCommand command) {
        final String username = command.username();
        final Long productId = command.productId();
        final int quantity = command.quantity();

        final Cart loadedCart =
            getActiveCartPort.apply(username)
                .orElseGet(() -> cartFactory.newCart(username));

        final Cart updatedCart = loadedCart.addProductToCart(productId, quantity);

        persistCartPort.accept(updatedCart);
    }
}
