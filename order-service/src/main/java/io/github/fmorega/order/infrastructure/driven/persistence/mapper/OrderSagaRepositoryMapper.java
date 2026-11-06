package io.github.fmorega.order.infrastructure.driven.persistence.mapper;

import io.github.fmorega.order.domain.OrderSaga;
import io.github.fmorega.order.infrastructure.driven.persistence.model.OrderSagaMO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderSagaRepositoryMapper {

  OrderSagaMO toMO(OrderSaga saga);

  default OrderSaga toDomain(OrderSagaMO mo) {
    return OrderSaga.restore(mo.getId(), mo.getOrderId(), mo.getStatus(), mo.getFailureReason(),
      mo.getCreatedAt(), mo.getUpdatedAt());
  }
}
