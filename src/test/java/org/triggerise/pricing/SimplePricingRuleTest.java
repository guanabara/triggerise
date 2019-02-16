package org.triggerise.pricing;

import org.junit.Before;
import org.junit.Test;
import org.triggerise.TestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimplePricingRuleTest {
    private SimplePricingRule victim;

    @Before
    public void setUp() {
        this.victim = new SimplePricingRule(2,1);
    }


    @Test
    public void shouldOnlyApplyDiscountToGivenProduct_When_InBasket() {
        List basket = Arrays.asList(TestUtils.TICKET, TestUtils.HOODIE, TestUtils.TICKET);

        Double totalHoodiePrice = victim.apply(TestUtils.HOODIE, basket);
        Double totalTicketPrice = victim.apply(TestUtils.TICKET, basket);

        assertEquals("Total hoodie price should be the same for single product", TestUtils.HOODIE.getPriceValue(), totalHoodiePrice);
        assertEquals("Total ticket price should be discounted 2/1", TestUtils.TICKET.getPriceValue(), totalTicketPrice);
    }

    @Test
    public void shouldReturnZero_When_NoProductsIsBasket() {
        List basket = Arrays.asList(TestUtils.TICKET, TestUtils.TICKET, TestUtils.TICKET);

        Double totalHoodiePrice = victim.apply(TestUtils.HOODIE, basket);

        assertEquals("Total hoodie price should be zero", 0.0, totalHoodiePrice, 0.0);
    }
}
