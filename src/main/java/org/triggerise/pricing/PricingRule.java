package org.triggerise.pricing;

import org.triggerise.domain.AbstractTriggeriseProduct;

import java.util.List;

/**
 * A pricing rule logic to applied for any given {@link AbstractTriggeriseProduct} in a given collection of
 * elements of the same type.
 */
public interface PricingRule {

    /**
     * Calculates the total value in products of a given type in the specified basket.
     * @param productType The product type to be calculated
     * @param basket The basket containing all the products.
     * @return The amount with the pricing rule applied.
     */
    Double apply(AbstractTriggeriseProduct productType, List<AbstractTriggeriseProduct> basket);
}
