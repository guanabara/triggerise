package org.triggerise.checkout;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.triggerise.TestUtils;
import org.triggerise.domain.AbstractTriggeriseProduct;
import org.triggerise.exceptions.UnknownProductException;
import org.triggerise.pricing.PricingRule;
import org.triggerise.pricing.ReducedPriceRule;
import org.triggerise.pricing.SimplePricingRule;
import org.triggerise.repository.pricing.PricingRulesRepository;
import org.triggerise.repository.products.ProductRepository;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CheckoutTest {
    private Checkout victim;
    private PricingRulesRepository rulesRepositoryMock;
    private ProductRepository productRepositoryMock;

    @Before
    public void setUp() {
        this.rulesRepositoryMock = Mockito.mock(PricingRulesRepository.class);
        this.productRepositoryMock = Mockito.mock(ProductRepository.class);

        when(this.productRepositoryMock.getProduct(TestUtils.HOODIE.getCode())).thenReturn(Optional.of(TestUtils.HOODIE));
        when(this.productRepositoryMock.getProduct(TestUtils.HAT.getCode())).thenReturn(Optional.of(TestUtils.HAT));
        when(this.productRepositoryMock.getProduct(TestUtils.TICKET.getCode())).thenReturn(Optional.of(TestUtils.TICKET));

        this.victim = new Checkout(productRepositoryMock, rulesRepositoryMock);
    }

    @Test(expected = UnknownProductException.class)
    public void shouldThrow_When_ProductCodeIsUnknown() throws UnknownProductException {
        when(this.productRepositoryMock.getProduct(any())).thenReturn(Optional.empty());

        this.victim.scan("this-is-an-unkown-product-code");
    }

    @Test
    public void shouldCallPricingRuleForEveryProductType_When_CalculatingAmount() {
        PricingRule hoodieRuleSpy = spy(new SimplePricingRule(1, 1));
        PricingRule hatRuleSpy = spy(new SimplePricingRule(3, 2));
        PricingRule ticketRuleSpy = spy(new SimplePricingRule(4, 2));

        when(this.rulesRepositoryMock.getRuleForProduct(TestUtils.HAT.getCode())).thenReturn(hatRuleSpy);
        when(this.rulesRepositoryMock.getRuleForProduct(TestUtils.HOODIE.getCode())).thenReturn(hoodieRuleSpy);
        when(this.rulesRepositoryMock.getRuleForProduct(TestUtils.TICKET.getCode())).thenReturn(ticketRuleSpy);

        List<AbstractTriggeriseProduct> basket = Arrays.asList(TestUtils.HAT, TestUtils.HOODIE, TestUtils.TICKET);

        basket.forEach(product -> {
            try {
                this.victim.scan(product.getCode());
            } catch (UnknownProductException e) {
                //die silently
            }
        });

        victim.total();

        verify(hoodieRuleSpy, times(1)).apply(eq(TestUtils.HOODIE), eq(basket));
        verify(hatRuleSpy, times(1)).apply(eq(TestUtils.HAT), eq(basket));
        verify(ticketRuleSpy, times(1)).apply(eq(TestUtils.TICKET), eq(basket));
    }

    @Test
    public void shouldSumAllPricesWithDiscounts__When_CalculatingAmount() {
        PricingRule ticketRule = new SimplePricingRule(2,1);
        PricingRule hoodieRule = new ReducedPriceRule(3,19.0);
        PricingRule hatRule = new SimplePricingRule(1,1);

        when(this.rulesRepositoryMock.getRuleForProduct(TestUtils.TICKET.getCode())).thenReturn(ticketRule);
        when(this.rulesRepositoryMock.getRuleForProduct(TestUtils.HOODIE.getCode())).thenReturn(hoodieRule);
        when(this.rulesRepositoryMock.getRuleForProduct(TestUtils.HAT.getCode())).thenReturn(hatRule);

        List basket1 = Arrays.asList(TestUtils.HAT, TestUtils.HOODIE, TestUtils.TICKET);
        List basket2 = Arrays.asList(TestUtils.TICKET, TestUtils.HOODIE, TestUtils.TICKET);
        List basket3 = Arrays.asList(TestUtils.HOODIE, TestUtils.HOODIE, TestUtils.HOODIE, TestUtils.TICKET, TestUtils.HOODIE);
        List basket4 = Arrays.asList(TestUtils.TICKET, TestUtils.HOODIE, TestUtils.TICKET,
                TestUtils.TICKET, TestUtils.HAT, TestUtils.HOODIE, TestUtils.HOODIE);

        Map<List<AbstractTriggeriseProduct>, Double> expectedAmountPerBasket = new HashMap<>();
        expectedAmountPerBasket.put(basket1, 32.5);
        expectedAmountPerBasket.put(basket2, 25.0);
        expectedAmountPerBasket.put(basket3, 81.0);
        expectedAmountPerBasket.put(basket4, 74.5);


        expectedAmountPerBasket.forEach((basket, basketAmount) -> {
            this.victim = new Checkout(productRepositoryMock, rulesRepositoryMock);

            basket.forEach(product -> {
                try {
                    this.victim.scan(product.getCode());
                } catch (UnknownProductException e) {
                    //die silently
                }
            });

            assertEquals("Basket total amount should be equal to sum of products discounted", basketAmount, victim.total());

        });
    }
}
