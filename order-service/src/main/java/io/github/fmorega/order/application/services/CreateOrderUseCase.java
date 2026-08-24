package io.github.fmorega.order.application.services;

import io.github.fmorega.order.application.ports.driven.OrderRepositoryPort;
import io.github.fmorega.order.application.ports.driving.CreateOrderServicePort;
import io.github.fmorega.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrderUseCase implements CreateOrderServicePort {

  private final OrderRepositoryPort orderRepositoryPort;

  @Override
  public Order createOrder(CreateOrderCommand command) {

    Order order = Order.create(
      UUID.randomUUID(),
      command.lines(),
      command.totalAmount(),
      command.currency(),
      command.paymentMethodReference(),
      Instant.now()
    );

    return orderRepositoryPort.save(order);
  }
}
