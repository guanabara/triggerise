package org.triggerise.repository.pricing;

import org.triggerise.pricing.PricingRule;
import org.triggerise.pricing.ReducedPriceRule;
import org.triggerise.pricing.SimplePricingRule;

import java.util.HashMap;
import java.util.Map;

public class OfflinePricingRulesRepository implements PricingRulesRepository {
    private final PricingRule DEFAULT_PRICING_RULE = new SimplePricingRule(1,1);
    private final Map<String, PricingRule> rules;

    public OfflinePricingRulesRepository() {
        this.rules = new HashMap<>();

        PricingRule twoForOnePricingRule = new SimplePricingRule(2,1);
        PricingRule discountOnThreeOrMore = new ReducedPriceRule(3, 19.0);

        this.rules.put("TICKET", twoForOnePricingRule);
        this.rules.put("HOODIE", discountOnThreeOrMore);
        this.rules.put("HAT", twoForOnePricingRule);
    }

    @Override
    public PricingRule getRuleForProduct(String code) {
        return this.rules.getOrDefault(code, DEFAULT_PRICING_RULE);
    }
}
