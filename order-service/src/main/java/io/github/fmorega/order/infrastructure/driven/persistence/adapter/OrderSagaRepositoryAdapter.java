package io.github.fmorega.order.infrastructure.driven.persistence.adapter;

import io.github.fmorega.order.application.ports.driven.OrderSagaRepositoryPort;
import io.github.fmorega.order.domain.OrderSaga;
import io.github.fmorega.order.infrastructure.driven.persistence.mapper.OrderSagaRepositoryMapper;
import io.github.fmorega.order.infrastructure.driven.persistence.repository.OrderSagaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderSagaRepositoryAdapter implements OrderSagaRepositoryPort {

  private final OrderSagaJpaRepository orderSagaJpaRepository;
  private final OrderSagaRepositoryMapper orderSagaRepositoryMapper;

  @Override
  public OrderSaga save(OrderSaga saga) {
    return orderSagaRepositoryMapper.toDomain(orderSagaJpaRepository.save(orderSagaRepositoryMapper.toMO(saga)));
  }
}
