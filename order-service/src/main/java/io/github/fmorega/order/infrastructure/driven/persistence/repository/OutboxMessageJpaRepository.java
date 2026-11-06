package io.github.fmorega.order.infrastructure.driven.persistence.repository;

import io.github.fmorega.order.infrastructure.driven.persistence.model.OutboxMessageMO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OutboxMessageJpaRepository extends JpaRepository<OutboxMessageMO, UUID> {

  @Query(value = """
            SELECT *
            FROM outbox_messages
            WHERE published_at IS NULL
            ORDER BY created_at, id
            LIMIT :batchSize
            FOR UPDATE SKIP LOCKED
            """, nativeQuery = true)
  List<OutboxMessageMO> findPendingForUpdate(@Param("batchSize") int batchSize);
}
