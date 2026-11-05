package io.github.fmorega.order.infrastructure.driven.persistence.mapper;

import io.github.fmorega.order.domain.Order;
import io.github.fmorega.order.domain.OrderLine;
import io.github.fmorega.order.infrastructure.driven.persistence.model.OrderMO;
import io.github.fmorega.order.infrastructure.driven.persistence.model.OrderLineMO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderRepositoryMapper {

  OrderMO toMO(Order order);

  OrderLineMO toMO(OrderLine line);

  default Order toDomain(OrderMO mo) {
    var lines = mo.getLines().stream()
      .map(line -> new OrderLine(line.getSku(), line.getQuantity()))
      .toList();

    return Order.restore(mo.getId(), lines, mo.getTotalAmount(), mo.getCurrency(),
      mo.getPaymentMethodReference(), mo.getStatus(), mo.getCreatedAt(), mo.getUpdatedAt());
  }
}
