package dev.vavelin.sunrise.shop.cart.command.domain;

import dev.vavelin.framework.shared.valueobjects.Price;
import dev.vavelin.framework.spring.stereotypes.DomainService;
import dev.vavelin.sunrise.shop.cart.command.usecase.GetNewestPriceListPort;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@DomainService
public class CartPriceDomainService {

    /**
     * Recalculates the cart price based on the provided cart.
     *
     * @param cart      the cart object containing the cart items
     * @param priceList the price list based on which the cart items are calculated
     * @return the calculated cart price
     */
    public CartPrice apply(Cart cart, GetNewestPriceListPort.PriceList priceList) {
        Map<Cart.CartItem, Price> priceCartItemMap =
            cart.getCartItems().stream().collect(Collectors.toMap(
                Function.identity(),
                cartItem -> priceList.getPrice(cartItem.productId())
                    .multiplyByQuantity(cartItem.quantity())
            ));

        return new CartPrice(priceCartItemMap);
    }

}
