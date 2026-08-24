package io.github.fmorega.order.application.ports.driving;

import io.github.fmorega.order.domain.Order;

import java.util.UUID;

public interface GetOrderServicePort {

  Order getOrder(UUID orderId);
}
