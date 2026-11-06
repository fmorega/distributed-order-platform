package io.github.fmorega.order.domain;

public enum OrderSagaStatus {
  WAITING_INVENTORY,
  WAITING_PAYMENT,
  COMPENSATING,
  COMPLETED,
  CANCELLED,
  REQUIRES_REVIEW
}
