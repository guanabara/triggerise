package org.triggerise.domain;

import java.util.Currency;

public abstract class AbstractTriggeriseProduct {
    public abstract String getCode();
    public abstract String getName();
    public abstract Double getPriceValue();
    public abstract Currency getPriceCurrency();
}
