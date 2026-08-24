package io.github.fmorega.order.application.exception;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException(UUID orderId) {
    super("Order %s not found".formatted(orderId));
  }
}
