package org.triggerise.checkout;

import org.triggerise.exceptions.UnknownProductException;

/**
 * Defines the checkout process minimal features.
 */
public interface CheckoutProcess {

    /**
     * Adds a product with the given producCode to basket.
     * @param productCode The code of the product to be added to the basket.
     * @throws UnknownProductException if the specified productCode is unknown.
     */
    void scan(String productCode) throws UnknownProductException;

    /**
     * Calculates the total amount to be paid at any time given the products in basket.
     * @return total amount to be paid.
     */
    Double total();
}
