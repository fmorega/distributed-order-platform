package io.github.fmorega.order.infrastructure.driven.persistence.repository;

import io.github.fmorega.order.infrastructure.driven.persistence.model.OrderSagaMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderSagaJpaRepository extends JpaRepository<OrderSagaMO, UUID> {
}
