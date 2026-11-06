package io.github.fmorega.order.infrastructure.driven.persistence.model;

import io.github.fmorega.order.domain.OrderSagaStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_sagas")
public class OrderSagaMO {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "order_id", nullable = false, unique = true)
  private UUID orderId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 32)
  private OrderSagaStatus status;

  @Column(name = "failure_reason", columnDefinition = "text")
  private String failureReason;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;
}
