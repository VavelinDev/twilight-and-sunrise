package dev.vavelin.twilight.shop.cart.command.infrastructure.jpa;

import dev.vavelin.twilight.shop.cart.command.domain.GetNewestPriceListPort;
import dev.vavelin.framework.shared.valueobjects.Price;
import dev.vavelin.framework.spring.stereotypes.OutputAdapter;

import java.math.BigDecimal;
import java.util.Map;

@OutputAdapter
class PriceListRepositoryJpaAdapter implements GetNewestPriceListPort {

    public static final int PRICE_SCALE = 4;

    @Override
    public PriceList get() {
        return new PriceList(Map.of(
            1L, new Price(BigDecimal.valueOf(10, PRICE_SCALE)),
            2L, new Price(BigDecimal.valueOf(20, PRICE_SCALE))
        ));
    }
}
