package com.waqas.orders;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class Order {
    public enum Status { DRAFT, CONFIRMED, CANCELLED }
    public record Line(String sku, int quantity, Money unitPrice) {
        public Line { if (sku == null || sku.isBlank() || quantity < 1) throw new IllegalArgumentException("invalid line"); }
        Money subtotal() { return unitPrice.multiply(quantity); }
    }
    private final UUID id = UUID.randomUUID();
    private final List<Line> lines;
    private Status status = Status.DRAFT;

    public Order(List<Line> lines) {
        this.lines = List.copyOf(lines);
        if (this.lines.isEmpty()) throw new IllegalArgumentException("an order requires at least one line");
    }
    public UUID id() { return id; }
    public Status status() { return status; }
    public Money total() { return lines.stream().map(Line::subtotal).reduce((a, b) -> a.add(b)).orElseThrow(); }
    public void confirm() { transition(Status.DRAFT, Status.CONFIRMED); }
    public void cancel() { transition(Status.DRAFT, Status.CANCELLED); }
    private void transition(Status expected, Status target) {
        if (status != expected) throw new IllegalStateException("cannot transition " + status + " to " + target);
        status = target;
    }
}
