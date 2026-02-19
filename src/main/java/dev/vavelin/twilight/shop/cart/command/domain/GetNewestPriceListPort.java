package dev.vavelin.twilight.shop.cart.command.domain;

import dev.vavelin.framework.shared.exceptions.ResourceNotFoundException;
import dev.vavelin.framework.shared.valueobjects.Price;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

@FunctionalInterface
public interface GetNewestPriceListPort extends Supplier<GetNewestPriceListPort.PriceList> {
    PriceList get();

    record PriceList(Map<Long, Price> priceByProduct) {
        public PriceList {
            priceByProduct = Map.copyOf(priceByProduct);
        }

        public Price getPrice(Long productId) {
            return Optional.ofNullable(priceByProduct.get(productId))
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Price not found for product ID: " + productId));
        }
    }
}
