package io.github.fmorega.order.infrastructure.driven.persistence.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "outbox_messages")
public class OutboxMessageMO {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "aggregate_id", nullable = false)
  private UUID aggregateId;

  @Column(name = "saga_id")
  private UUID sagaId;

  @Column(name = "correlation_id", nullable = false)
  private UUID correlationId;

  @Column(name = "causation_id")
  private UUID causationId;

  @Column(name = "message_type", nullable = false, length = 100)
  private String messageType;

  @Column(name = "destination", nullable = false, length = 100)
  private String destination;

  @Column(name = "message_key", nullable = false, length = 100)
  private String messageKey;

  @Column(name = "version", nullable = false)
  private int version;

  @JdbcTypeCode(SqlTypes.JSON)
  @Column(name = "payload", nullable = false, columnDefinition = "jsonb")
  private Map<String, Object> payload;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "published_at")
  private Instant publishedAt;

  @Column(name = "attempts", nullable = false)
  private int attempts;
}
