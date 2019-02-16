package org.triggerise.repository.products;

import org.triggerise.domain.AbstractTriggeriseProduct;
import org.triggerise.domain.TriggeriseProduct;

import java.util.Currency;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class OfflineProductRepository implements ProductRepository {

    private final Set<AbstractTriggeriseProduct> products;

    public OfflineProductRepository(){
        this.products = new HashSet<>();

        this.products.add(TICKET);
        this.products.add(HOODIE);
        this.products.add(HAT);
    }

    @Override
    public Optional<AbstractTriggeriseProduct> getProduct(String code) {
        return this.products.stream()
                .filter(product -> product.getCode().equals(code))
                .findFirst();
    }

    private final Currency defaultCurrency = Currency.getInstance("EUR");

    private final TriggeriseProduct TICKET = TriggeriseProduct.Builder.aTriggeriseProduct()
            .withCode("TICKET")
            .withName("Triggerise Ticket")
            .withPriceValue(5.0d)
            .withPriceCurrency(defaultCurrency)
            .build();

    private final TriggeriseProduct HOODIE = TriggeriseProduct.Builder.aTriggeriseProduct()
            .withCode("HOODIE")
            .withName("Triggerise Hoodie")
            .withPriceValue(20.0d)
            .withPriceCurrency(defaultCurrency)
            .build();

    private final TriggeriseProduct HAT = TriggeriseProduct.Builder.aTriggeriseProduct()
            .withCode("HAT")
            .withName("Triggerise Hat")
            .withPriceValue(7.5d)
            .withPriceCurrency(defaultCurrency)
            .build();
}
