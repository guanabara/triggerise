package org.triggerise.domain;

import java.util.Currency;
import java.util.Objects;

public class TriggeriseProduct extends AbstractTriggeriseProduct {
    private final String code;
    private final String name;
    private final Double priceValue;
    private final Currency priceCurrency;


    private TriggeriseProduct(String code, String name, Double price, Currency priceCurrency) {
        this.code = code;
        this.name = name;
        this.priceValue = price;
        this.priceCurrency = priceCurrency;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Double getPriceValue() {
        return this.priceValue;
    }

    @Override
    public Currency getPriceCurrency() {
        return this.priceCurrency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TriggeriseProduct that = (TriggeriseProduct) o;
        return code.equals(that.code);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TriggeriseProduct{");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", priceValue=").append(priceValue);
        sb.append(", priceCurrency=").append(priceCurrency);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public static final class Builder {
        private String code;
        private String name;
        private Double priceValue;
        private Currency priceCurrency;

        private Builder() {
        }

        public static Builder aTriggeriseProduct() {
            return new Builder();
        }

        public Builder withCode(String code) {
            this.code = code;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withPriceValue(Double priceValue) {
            this.priceValue = priceValue;
            return this;
        }

        public Builder withPriceCurrency(Currency priceCurrency) {
            this.priceCurrency = priceCurrency;
            return this;
        }

        public TriggeriseProduct build() {
            return new TriggeriseProduct(code, name, priceValue, priceCurrency);
        }
    }
}
