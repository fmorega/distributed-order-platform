package io.github.fmorega.order.application.services;

import io.github.fmorega.order.domain.OrderLine;

import java.math.BigDecimal;
import java.util.List;

public record CreateOrderCommand(
  List<OrderLine> lines,
  BigDecimal totalAmount,
  String currency,
  String paymentMethodReference
) {
}
