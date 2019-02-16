package org.triggerise.repository.pricing;

import org.triggerise.pricing.PricingRule;

public interface PricingRulesRepository {
    PricingRule getRuleForProduct(String code);
}
