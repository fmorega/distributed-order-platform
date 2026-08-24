package io.github.fmorega.order.application.ports.driven;

import io.github.fmorega.order.domain.Order;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepositoryPort {

  Order save(Order order);

  Optional<Order> findById(UUID orderId);
}
