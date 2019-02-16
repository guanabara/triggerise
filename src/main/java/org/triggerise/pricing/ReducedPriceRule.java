package org.triggerise.pricing;

import org.triggerise.domain.AbstractTriggeriseProduct;

import java.util.List;

/**
 * Implementation of a {@link PricingRule} where when the basket contains more than a given amount of products of
 * the given type, each product has its price reduced to the new given value.
 */
public class ReducedPriceRule implements PricingRule {

    private final Integer minAmountToApply;
    private final Double discountedPrice;

    public ReducedPriceRule(Integer minAmountToApply, Double discountedPrice) {
        this.minAmountToApply = minAmountToApply;
        this.discountedPrice = discountedPrice;
    }

    @Override
    public Double apply(AbstractTriggeriseProduct productType, List<AbstractTriggeriseProduct> basket) {
        Long relevantProductsCount = basket.stream()
                .filter(product -> product.getCode().equals(productType.getCode()))
                .count();

        if (relevantProductsCount >= minAmountToApply) {
            return relevantProductsCount * discountedPrice;
        } else {
            return relevantProductsCount * productType.getPriceValue();
        }
    }
}
