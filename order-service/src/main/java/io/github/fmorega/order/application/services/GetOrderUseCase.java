package io.github.fmorega.order.application.services;

import io.github.fmorega.order.application.exception.OrderNotFoundException;
import io.github.fmorega.order.application.ports.driven.OrderRepositoryPort;
import io.github.fmorega.order.application.ports.driving.GetOrderServicePort;
import io.github.fmorega.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetOrderUseCase implements GetOrderServicePort {

  private final OrderRepositoryPort orderRepositoryPort;

  @Override
  public Order getOrder(UUID orderId) {
    return orderRepositoryPort.findById(orderId)
      .orElseThrow(() ->
        new OrderNotFoundException(orderId)
      );

  }
}
