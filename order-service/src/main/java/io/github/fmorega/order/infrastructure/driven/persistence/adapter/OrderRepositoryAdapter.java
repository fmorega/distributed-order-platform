package io.github.fmorega.order.infrastructure.driven.persistence.adapter;

import io.github.fmorega.order.application.ports.driven.OrderRepositoryPort;
import io.github.fmorega.order.domain.Order;
import io.github.fmorega.order.infrastructure.driven.persistence.mapper.OrderRepositoryMapper;
import io.github.fmorega.order.infrastructure.driven.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

  private final OrderJpaRepository orderJpaRepository;
  private final OrderRepositoryMapper orderRepositoryMapper;

  @Override
  public Order save(Order order) {
    return orderRepositoryMapper.toDomain(orderJpaRepository.save(orderRepositoryMapper.toMO(order)));
  }

  @Override
  public Optional<Order> findById(UUID orderId) {
    return orderJpaRepository.findById(orderId).map(orderRepositoryMapper::toDomain);
  }
}
