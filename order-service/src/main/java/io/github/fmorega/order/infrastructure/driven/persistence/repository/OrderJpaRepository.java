package io.github.fmorega.order.infrastructure.driven.persistence.repository;

import io.github.fmorega.order.infrastructure.driven.persistence.model.OrderMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderMO, UUID> {
}
