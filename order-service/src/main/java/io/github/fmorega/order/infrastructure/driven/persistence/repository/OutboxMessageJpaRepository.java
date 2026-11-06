package io.github.fmorega.order.infrastructure.driven.persistence.repository;

import io.github.fmorega.order.infrastructure.driven.persistence.model.OutboxMessageMO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxMessageJpaRepository extends JpaRepository<OutboxMessageMO, UUID> {
}
