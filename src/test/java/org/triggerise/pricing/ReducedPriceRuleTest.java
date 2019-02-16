package org.triggerise.pricing;

import org.junit.Before;
import org.junit.Test;
import org.triggerise.TestUtils;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReducedPriceRuleTest {
    private ReducedPriceRule victim;

    @Before
    public void setUp() {
        this.victim = new ReducedPriceRule(3,3.2);
    }

    @Test
    public void shouldOnlyApplyDiscountWhenMeetsCriteria_When_InBasket() {
        List basket = Arrays.asList(TestUtils.TICKET, TestUtils.HOODIE, TestUtils.TICKET,
                TestUtils.HOODIE, TestUtils.HOODIE, TestUtils.HOODIE, TestUtils.HOODIE);

        Double totalHoodiePrice = victim.apply(TestUtils.HOODIE, basket);
        Double totalTicketPrice = victim.apply(TestUtils.TICKET, basket);

        Double expectedHoodiePrice = 5*3.2;
        Double expectedTicketPrice = 2 * TestUtils.TICKET.getPriceValue();

        assertEquals("Total hoodie price should be the reduced for every product", expectedHoodiePrice, totalHoodiePrice);
        assertEquals("Total ticket price should sum of all tickets", expectedTicketPrice, totalTicketPrice);
    }

    @Test
    public void shouldReturnZero_When_NoProductsIsBasket() {
        List basket = Arrays.asList(TestUtils.TICKET, TestUtils.TICKET, TestUtils.TICKET);

        Double totalHoodiePrice = victim.apply(TestUtils.HOODIE, basket);

        assertEquals("Total hoodie price should be zero", 0.0, totalHoodiePrice, 0.0);
    }
}
