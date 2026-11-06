package io.github.fmorega.order.application.model;

import io.github.fmorega.order.domain.Order;
import io.github.fmorega.order.domain.OrderSaga;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public record OutboxMessage(
  UUID id,
  UUID aggregateId,
  UUID sagaId,
  UUID correlationId,
  UUID causationId,
  String messageType,
  String destination,
  String messageKey,
  int version,
  Map<String, Object> payload,
  Instant createdAt
) {

  public static OutboxMessage reserveInventory(Order order, OrderSaga saga, Instant now) {
    var lines = order.getLines().stream()
      .map(line -> Map.<String, Object>of("sku", line.sku(), "quantity", line.quantity()))
      .toList();

    Map<String, Object> payload = Map.of(
      "orderId", order.getId().toString(),
      "sagaId", saga.getId().toString(),
      "lines", lines
    );

    return new OutboxMessage(UUID.randomUUID(), order.getId(), saga.getId(), order.getId(), null,
      "ReserveInventory", "inventory.commands", order.getId().toString(), 1, payload, now);
  }
}
