package com.vavelin.twilight.shop.cart.command.domain;

import com.vavelin.twilight.shop.shared.valueobjects.Price;
import java.util.Map;
import java.util.function.Supplier;

@FunctionalInterface
public interface GetNewestPriceListPort extends Supplier<GetNewestPriceListPort.PriceList> {
    PriceList get();

    record PriceList(Map<Long, Price> priceByProduct) {
        public PriceList {
            priceByProduct = Map.copyOf(priceByProduct);
        }

        public Price getPrice(Long productId) {
            return priceByProduct.get(productId);
        }
    }
}
