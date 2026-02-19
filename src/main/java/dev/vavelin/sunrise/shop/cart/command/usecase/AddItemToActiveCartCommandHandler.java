package dev.vavelin.sunrise.shop.cart.command.usecase;

import dev.vavelin.framework.shared.cqrs.command.CommandHandler;
import dev.vavelin.framework.spring.stereotypes.CommandHandlerService;
import dev.vavelin.sunrise.shop.cart.command.domain.Cart;
import dev.vavelin.sunrise.shop.cart.command.domain.CartFactory;
import dev.vavelin.sunrise.shop.cart.command.domain.CartPrice;
import dev.vavelin.sunrise.shop.cart.command.domain.CartPriceDomainService;

@CommandHandlerService
class AddItemToActiveCartCommandHandler implements
        CommandHandler<AddItemToActiveCartCommand> {

    private final GetActiveCartPort getActiveCartPort;
    private final GetNewestPriceListPort getNewestPriceListPort;
    private final PersistCartPort persistCartPort;
    private final CartFactory cartFactory;
    private final CartPriceDomainService cartPriceDomainService;

    AddItemToActiveCartCommandHandler(GetActiveCartPort getActiveCartPort,
            GetNewestPriceListPort getNewestPriceListPort,
            PersistCartPort persistCartPort,
            CartFactory cartFactory,
            CartPriceDomainService cartPriceDomainService) {
        this.getActiveCartPort = getActiveCartPort;
        this.getNewestPriceListPort = getNewestPriceListPort;
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

        GetNewestPriceListPort.PriceList priceList = getNewestPriceListPort.get();

        final CartPrice cartPrice = cartPriceDomainService.apply(updatedCart, priceList);

        // SHOWCASE: cartPrice could be stored, logged, or returned to the caller

        persistCartPort.accept(updatedCart);
    }
}
