package com.vavelin.example.hexagon.cart.command.domain;

import com.vavelin.example.hexagon.shared.ddd.DomainPolicy;
import com.vavelin.example.hexagon.shared.domain.vo.Price;
import com.vavelin.example.hexagon.spring.stereotypes.Policy;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Policy
class CartPriceDomainService implements DomainPolicy<Cart, CartPrice> {

    private final GetNewestPriceListPort getNewestPriceListPort;

    // TODO: Here we will add a policy for possible discounts

    public CartPriceDomainService(GetNewestPriceListPort getNewestPriceListPort) {
        this.getNewestPriceListPort = getNewestPriceListPort;
    }

    /**
     * Recalculates the cart price based on the provided cart.
     *
     * @param cart the cart object containing the cart items
     * @return the calculated cart price
     */
    @Override
    public CartPrice apply(Cart cart) {
        GetNewestPriceListPort.PriceList priceList = getNewestPriceListPort.getNewest();

        Map<Cart.CartItem, Price> priceCartItemMap =
            cart.getCartItems().stream().collect(Collectors.toMap(
                Function.identity(),
                cartItem -> priceList.getPrice(cartItem.productId())
                    .multiplyByQuantity(cartItem.quantity())
            ));

        return new CartPrice(priceCartItemMap);
    }

}