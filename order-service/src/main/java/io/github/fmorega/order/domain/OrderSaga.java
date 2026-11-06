package io.github.fmorega.order.domain;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class OrderSaga {

  private final UUID id;
  private final UUID orderId;
  private OrderSagaStatus status;
  private String failureReason;
  private final Instant createdAt;
  private Instant updatedAt;

  private OrderSaga(UUID id, UUID orderId, OrderSagaStatus status, String failureReason, Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.orderId = orderId;
    this.status = status;
    this.failureReason = failureReason;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static OrderSaga start(UUID orderId, Instant now) {
    return new OrderSaga(UUID.randomUUID(), orderId, OrderSagaStatus.WAITING_INVENTORY, null, now, now);
  }

  public static OrderSaga restore(UUID id, UUID orderId, OrderSagaStatus status, String failureReason,
                                  Instant createdAt, Instant updatedAt) {
    return new OrderSaga(id, orderId, status, failureReason, createdAt, updatedAt);
  }
}
