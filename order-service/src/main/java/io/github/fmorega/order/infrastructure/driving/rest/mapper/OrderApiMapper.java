package io.github.fmorega.order.infrastructure.driving.rest.mapper;

import io.github.fmorega.order.api.model.CreateOrderLine;
import io.github.fmorega.order.api.model.CreateOrderRequest;
import io.github.fmorega.order.api.model.OrderLineResponse;
import io.github.fmorega.order.api.model.OrderResponse;
import io.github.fmorega.order.application.services.CreateOrderCommand;
import io.github.fmorega.order.domain.Order;
import io.github.fmorega.order.domain.OrderLine;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderApiMapper {

  CreateOrderCommand toCommand(CreateOrderRequest request);

  OrderLine toDomain(CreateOrderLine line);

  OrderResponse toResponse(Order order);

  OrderLineResponse toResponse(OrderLine line);

  default OffsetDateTime toOffsetDateTime(Instant instant) {
    return instant == null ? null : instant.atOffset(ZoneOffset.UTC);
  }
}
