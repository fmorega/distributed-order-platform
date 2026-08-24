package io.github.fmorega.order.application.ports.driving;

import io.github.fmorega.order.application.services.CreateOrderCommand;
import io.github.fmorega.order.domain.Order;

public interface CreateOrderServicePort {

  Order createOrder(CreateOrderCommand command);
}
