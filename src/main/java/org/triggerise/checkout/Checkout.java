package org.triggerise.checkout;

import org.triggerise.domain.AbstractTriggeriseProduct;
import org.triggerise.exceptions.UnknownProductException;
import org.triggerise.repository.pricing.PricingRulesRepository;
import java.util.List;
import org.triggerise.repository.products.ProductRepository;

import java.util.ArrayList;
import java.util.Optional;

public class Checkout implements CheckoutProcess {

    private final List<AbstractTriggeriseProduct> basket;
    private final ProductRepository productRepository;
    private final PricingRulesRepository pricingRulesRepository;

    public Checkout(ProductRepository productRepository, PricingRulesRepository pricingRulesRepository) {
        this.productRepository = productRepository;
        this.pricingRulesRepository = pricingRulesRepository;

        this.basket = new ArrayList<>();
    }

    @Override
    public void scan(String productCode) throws UnknownProductException {
        this.productRepository.getProduct(productCode)
                .map(this.basket::add)
                .orElseThrow(() -> new UnknownProductException(productCode));
    }

    @Override
    public Double total() {
        return this.basket.stream()
                .map(AbstractTriggeriseProduct::getCode)
                .distinct()
                .map(this.productRepository::getProduct)
                .map(Optional::get)
                .mapToDouble(this::getTotalForProduct)
                .sum();
    }

    private Double getTotalForProduct(AbstractTriggeriseProduct product) {
        return this.pricingRulesRepository.getRuleForProduct(product.getCode())
                .apply(product, this.basket);
    }
}
