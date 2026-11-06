package io.github.fmorega.order.application.services;

import io.github.fmorega.order.application.model.OutboxMessage;
import io.github.fmorega.order.application.ports.driven.OrderRepositoryPort;
import io.github.fmorega.order.application.ports.driven.OrderSagaRepositoryPort;
import io.github.fmorega.order.application.ports.driven.OutboxRepositoryPort;
import io.github.fmorega.order.application.ports.driving.CreateOrderServicePort;
import io.github.fmorega.order.domain.Order;
import io.github.fmorega.order.domain.OrderSaga;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase implements CreateOrderServicePort {

  private final OrderRepositoryPort orderRepositoryPort;
  private final OrderSagaRepositoryPort orderSagaRepositoryPort;
  private final OutboxRepositoryPort outboxRepositoryPort;

  @Override
  @Transactional
  public Order createOrder(CreateOrderCommand command) {
    Instant now = Instant.now();

    Order order = Order.create(UUID.randomUUID(), command.lines(), command.totalAmount(),
      command.currency(), command.paymentMethodReference(), now);

    order = orderRepositoryPort.save(order);

    OrderSaga saga = OrderSaga.start(order.getId(), now);
    orderSagaRepositoryPort.save(saga);

    OutboxMessage message = OutboxMessage.reserveInventory(order, saga, now);
    outboxRepositoryPort.save(message);

    return order;
  }
}
