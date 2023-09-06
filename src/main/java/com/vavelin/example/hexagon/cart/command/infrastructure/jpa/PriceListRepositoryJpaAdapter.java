package com.vavelin.example.hexagon.cart.command.infrastructure.jpa;

import com.vavelin.example.hexagon.cart.command.domain.GetNewestPriceListPort;
import com.vavelin.example.hexagon.shared.valueobjects.Price;
import com.vavelin.example.hexagon.spring.stereotypes.Adapter;
import java.math.BigDecimal;
import java.util.Map;

@Adapter
public class PriceListRepositoryJpaAdapter implements GetNewestPriceListPort {

    public static final int PRICE_SCALE = 4;

    @Override
    public PriceList getNewest() {
        return new PriceList(Map.of(
            1L, new Price(BigDecimal.valueOf(10, PRICE_SCALE)),
            2L, new Price(BigDecimal.valueOf(20, PRICE_SCALE))
        ));
    }
}
