package org.triggerise.repository.products;

import org.triggerise.domain.AbstractTriggeriseProduct;

import java.util.Optional;

public interface ProductRepository {
    Optional<AbstractTriggeriseProduct> getProduct(String code);
}
