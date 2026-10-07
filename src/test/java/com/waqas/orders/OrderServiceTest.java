package com.waqas.orders;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public final class OrderServiceTest {
    public static void main(String[] args) {
        var usd = Currency.getInstance("USD");
        var order = new Order(List.of(new Order.Line("CHAIR", 2, new Money(new BigDecimal("19.95"), usd))));
        assert order.total().amount().equals(new BigDecimal("39.90"));
        order.confirm();
        assert order.status() == Order.Status.CONFIRMED;
        try { order.cancel(); throw new AssertionError("transition should fail"); } catch (IllegalStateException expected) { }
    }
}
