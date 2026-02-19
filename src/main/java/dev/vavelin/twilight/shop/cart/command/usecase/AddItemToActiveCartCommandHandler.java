package dev.vavelin.twilight.shop.cart.command.usecase;

import dev.vavelin.twilight.shop.cart.command.domain.Cart;
import dev.vavelin.twilight.shop.cart.command.domain.CartFactory;
import dev.vavelin.twilight.shop.cart.command.domain.CartPrice;
import dev.vavelin.twilight.shop.cart.command.domain.CartPriceDomainService;
import dev.vavelin.framework.shared.cqrs.command.CommandHandler;
import dev.vavelin.framework.spring.stereotypes.CommandHandlerService;

@CommandHandlerService
class AddItemToActiveCartCommandHandler implements
        CommandHandler<AddItemToActiveCartCommand> {

    private final GetActiveCartPort getActiveCartPort;
    private final PersistCartPort persistCartPort;
    private final CartFactory cartFactory;
    private final CartPriceDomainService cartPriceDomainService;

    AddItemToActiveCartCommandHandler(GetActiveCartPort getActiveCartPort,
            PersistCartPort persistCartPort,
            CartFactory cartFactory,
            CartPriceDomainService cartPriceDomainService) {
        this.getActiveCartPort = getActiveCartPort;
        this.persistCartPort = persistCartPort;
        this.cartFactory = cartFactory;
        this.cartPriceDomainService = cartPriceDomainService;
    }

    @Override
    public void accept(AddItemToActiveCartCommand command) {
        final String username = command.username();
        final Long productId = command.productId();
        final int quantity = command.quantity();

        final Cart loadedCart = getActiveCartPort.apply(username)
                .orElseGet(() -> cartFactory.newCart(username));

        final Cart updatedCart = loadedCart.addProductToCart(productId, quantity);

        final CartPrice cartPrice = cartPriceDomainService.apply(updatedCart);

        // SHOWCASE: cartPrice could be stored, logged, or returned to the caller

        persistCartPort.accept(updatedCart);
    }
}
