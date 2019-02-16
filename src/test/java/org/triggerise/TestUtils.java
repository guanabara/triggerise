package org.triggerise;

import org.triggerise.domain.TriggeriseProduct;

import java.util.Currency;

public final class TestUtils {
    public static final TriggeriseProduct TICKET = TriggeriseProduct.Builder.aTriggeriseProduct()
            .withCode("TICKET")
            .withName("Triggerise Ticket")
            .withPriceValue(5.0d)
            .withPriceCurrency(Currency.getInstance("EUR"))
            .build();

    public static final TriggeriseProduct HOODIE = TriggeriseProduct.Builder.aTriggeriseProduct()
            .withCode("HOODIE")
            .withName("Triggerise Hoodie")
            .withPriceValue(20.0d)
            .withPriceCurrency(Currency.getInstance("EUR"))
            .build();

    public static final TriggeriseProduct HAT = TriggeriseProduct.Builder.aTriggeriseProduct()
            .withCode("HAT")
            .withName("Triggerise Hat")
            .withPriceValue(7.5d)
            .withPriceCurrency(Currency.getInstance("EUR"))
            .build();
}
