package com.vavelin.twilight.shop.shared.valueobjects;

import java.math.BigDecimal;

/**
 * Value Object representing a price.
 *
 * @param netPrice
 */
public record Price(BigDecimal netPrice) {

    public static Price ZERO = new Price(BigDecimal.ZERO);

    public Price add(Price price) {
        return new Price(netPrice.add(price.netPrice));
    }

    public Price multiplyByQuantity(int quantity) {
        return new Price(netPrice.multiply(BigDecimal.valueOf(quantity)));
    }
}
