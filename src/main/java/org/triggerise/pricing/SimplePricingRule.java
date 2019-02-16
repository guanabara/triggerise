package org.triggerise.pricing;

import org.triggerise.domain.AbstractTriggeriseProduct;

import java.util.List;

/**
 * Implementation of a {@link PricingRule} where every given amount of products is transformed in another amount of products.
 * Example: Given SimplePricingRule(2, 1), every 2 products in the basket will count as only one.
 */
public class SimplePricingRule implements PricingRule {
    private final Integer transformAmount;
    private final Integer intoAmount;

    public SimplePricingRule(Integer transformAmount, Integer intoAmount) {
        this.transformAmount = transformAmount;
        this.intoAmount = intoAmount;
    }

    @Override
    public Double apply(AbstractTriggeriseProduct type, List<AbstractTriggeriseProduct> basket) {
        Long relevantProductsCount = basket.stream()
                .filter(product -> product.getCode().equals(type.getCode()))
                .count();

        Integer productsToPay = Math.floorDiv(relevantProductsCount.intValue(), transformAmount) * intoAmount;

        productsToPay += Math.floorMod(relevantProductsCount.intValue(), transformAmount);

        return productsToPay * type.getPriceValue();
    }
}
