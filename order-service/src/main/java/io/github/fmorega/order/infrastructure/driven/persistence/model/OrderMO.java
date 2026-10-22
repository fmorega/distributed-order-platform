package io.github.fmorega.order.infrastructure.driven.persistence.model;

import io.github.fmorega.order.domain.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderMO {

  @Id
  @Column(name = "id", nullable = false)
  private UUID id;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 20)
  private OrderStatus status;

  @Column(name = "total_amount", nullable = false, precision = 19, scale = 2)
  private BigDecimal totalAmount;

  @Column(name = "currency", nullable = false, length = 3)
  private String currency;

  @Column(name = "payment_method_reference", nullable = false, length = 255)
  private String paymentMethodReference;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  @Column(name = "updated_at", nullable = false)
  private Instant updatedAt;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(name = "order_lines", joinColumns = @JoinColumn(name = "order_id"))
  @OrderColumn(name = "line_index")
  private List<OrderLineMO> lines = new ArrayList<>();

  public OrderMO(UUID id, OrderStatus status, BigDecimal totalAmount, String currency, String paymentMethodReference, Instant createdAt, Instant updatedAt, ArrayList<Object> lines) {
  }
}
