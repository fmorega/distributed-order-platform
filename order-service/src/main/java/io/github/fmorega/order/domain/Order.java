package io.github.fmorega.order.domain;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
public class Order {

  private final UUID id;
  private final List<OrderLine> lines;
  private final BigDecimal totalAmount;
  private final String currency;
  private final String paymentMethodReference;
  private final Instant createdAt;

  private OrderStatus status;
  private Instant updatedAt;

  private Order(UUID id, List<OrderLine> lines, BigDecimal totalAmount, String currency, String paymentMethodReference, Instant createdAt) {
    this.id = id;
    this.lines = List.copyOf(lines);
    this.totalAmount = totalAmount;
    this.currency = currency;
    this.paymentMethodReference = paymentMethodReference;
    this.status = OrderStatus.PENDING;
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
  }

  public static Order create(UUID id, List<OrderLine> lines, BigDecimal totalAmount, String currency, String paymentMethodReference, Instant createdAt) {
    validate(lines, totalAmount, currency, paymentMethodReference);

    return new Order(
      id,
      lines,
      totalAmount,
      currency,
      paymentMethodReference,
      createdAt
    );
  }

  public void confirm(Instant occurredAt) {
    ensurePending();

    status = OrderStatus.CONFIRMED;
    updatedAt = occurredAt;
  }

  public void cancel(Instant occurredAt) {
    ensurePending();

    status = OrderStatus.CANCELLED;
    updatedAt = occurredAt;
  }

  private void ensurePending() {
    if (status != OrderStatus.PENDING) {
      throw new IllegalStateException(
        "Order %s cannot transition from %s"
          .formatted(id, status)
      );
    }
  }

  private static void validate(List<OrderLine> lines, BigDecimal totalAmount, String currency, String paymentMethodReference) {
    if (lines == null || lines.isEmpty()) {
      throw new IllegalArgumentException(
        "Order must contain at least one line"
      );
    }

    if (totalAmount == null || totalAmount.signum() <= 0) {
      throw new IllegalArgumentException(
        "Total amount must be greater than zero"
      );
    }

    if (currency == null || !currency.matches("[A-Z]{3}")) {
      throw new IllegalArgumentException(
        "Currency must use ISO 4217 format"
      );
    }

    if (paymentMethodReference == null || paymentMethodReference.isBlank()) {

      throw new IllegalArgumentException(
        "Payment method reference must not be blank"
      );
    }
  }
}
