package io.github.fmorega.order.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class OrderTest {

  @Test
  void shouldCreatePendingOrder() {
    Order order = createOrder();

    assertThat(order.getId()).isNotNull();
    assertThat(order.getStatus()).isEqualTo(OrderStatus.PENDING);
    assertThat(order.getLines()).hasSize(1);
    assertThat(order.getCreatedAt()).isEqualTo(order.getUpdatedAt());
  }

  @Test
  void shouldConfirmPendingOrder() {
    Order order = createOrder();
    Instant confirmedAt = Instant.parse("2026-10-30T12:00:00Z");

    order.confirm(confirmedAt);

    assertThat(order.getStatus()).isEqualTo(OrderStatus.CONFIRMED);
    assertThat(order.getUpdatedAt()).isEqualTo(confirmedAt);
  }

  @Test
  void shouldRejectInvalidStateTransition() {
    Order order = createOrder();
    order.confirm(Instant.now());

    assertThatThrownBy(() -> order.cancel(Instant.now()))
      .isInstanceOf(IllegalStateException.class);
  }

  @Test
  void shouldRejectOrderWithoutLines() {
    assertThatThrownBy(() -> Order.create(UUID.randomUUID(), List.of(),
      new BigDecimal("49.90"), "EUR", "pm_test", Instant.now()))
      .isInstanceOf(IllegalArgumentException.class);
  }

  private Order createOrder() {
    return Order.create(UUID.randomUUID(), List.of(new OrderLine("SKU-001", 2)),
      new BigDecimal("49.90"), "EUR", "pm_test", Instant.now());
  }
}
