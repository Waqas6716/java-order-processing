package com.waqas.orders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {
    public Money {
        Objects.requireNonNull(amount, "amount is required");
        Objects.requireNonNull(currency, "currency is required");
        amount = amount.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_UP);
        if (amount.signum() < 0) throw new IllegalArgumentException("amount cannot be negative");
    }
    public Money multiply(int quantity) {
        if (quantity < 1) throw new IllegalArgumentException("quantity must be positive");
        return new Money(amount.multiply(BigDecimal.valueOf(quantity)), currency);
    }
    public Money add(Money other) {
        if (!currency.equals(other.currency)) throw new IllegalArgumentException("currency mismatch");
        return new Money(amount.add(other.amount), currency);
    }
}
