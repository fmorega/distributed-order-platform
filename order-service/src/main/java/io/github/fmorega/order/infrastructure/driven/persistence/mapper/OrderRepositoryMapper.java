package io.github.fmorega.order.infrastructure.driven.persistence.mapper;

import io.github.fmorega.order.domain.Order;
import io.github.fmorega.order.domain.OrderLine;
import io.github.fmorega.order.infrastructure.driven.persistence.model.OrderLineMO;
import io.github.fmorega.order.infrastructure.driven.persistence.model.OrderMO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class OrderRepositoryMapper {

  public OrderMO toMO(Order order) {
    var lines = order.getLines().stream()
      .map(line -> new OrderLineMO(line.sku(), line.quantity()))
      .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

    return new OrderMO(order.getId(), order.getStatus(), order.getTotalAmount(), order.getCurrency(),
      order.getPaymentMethodReference(), order.getCreatedAt(), order.getUpdatedAt(), lines);
  }

  public Order toDomain(OrderMO orderMO) {
    var lines = orderMO.getLines().stream()
      .map(line -> new OrderLine(line.getSku(), line.getQuantity()))
      .toList();

    return Order.restore(orderMO.getId(), lines, orderMO.getTotalAmount(), orderMO.getCurrency(),
      orderMO.getPaymentMethodReference(), orderMO.getStatus(), orderMO.getCreatedAt(), orderMO.getUpdatedAt());
  }
}
