package com.vavelin.twilight.shop.cart.command.domain;

import com.vavelin.twilight.shop.shared.valueobjects.Price;
import com.vavelin.twilight.shop.spring.stereotypes.DomainService;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@DomainService
public class CartPriceDomainService {

    private final GetNewestPriceListPort getNewestPriceListPort;

    CartPriceDomainService(GetNewestPriceListPort getNewestPriceListPort) {
        this.getNewestPriceListPort = getNewestPriceListPort;
    }

    /**
     * Recalculates the cart price based on the provided cart.
     *
     * @param cart the cart object containing the cart items
     * @return the calculated cart price
     */
    public CartPrice apply(Cart cart) {
        GetNewestPriceListPort.PriceList priceList = getNewestPriceListPort.get();

        Map<Cart.CartItem, Price> priceCartItemMap =
            cart.getCartItems().stream().collect(Collectors.toMap(
                Function.identity(),
                cartItem -> priceList.getPrice(cartItem.productId())
                    .multiplyByQuantity(cartItem.quantity())
            ));

        return new CartPrice(priceCartItemMap);
    }

}
