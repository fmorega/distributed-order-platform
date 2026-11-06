package io.github.fmorega.order.application.ports.driven;

import io.github.fmorega.order.domain.OrderSaga;

public interface OrderSagaRepositoryPort {

  OrderSaga save(OrderSaga saga);
}
