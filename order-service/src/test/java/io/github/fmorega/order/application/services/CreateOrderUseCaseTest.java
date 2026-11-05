package io.github.fmorega.order.application.services;

import io.github.fmorega.order.application.ports.driven.OrderRepositoryPort;
import io.github.fmorega.order.domain.Order;
import io.github.fmorega.order.domain.OrderLine;
import io.github.fmorega.order.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateOrderUseCaseTest {

  @Mock
  private OrderRepositoryPort orderRepositoryPort;

  @Test
  void shouldCreateAndPersistPendingOrder() {
    CreateOrderUseCase useCase = new CreateOrderUseCase(orderRepositoryPort);

    when(orderRepositoryPort.save(any(Order.class)))
      .thenAnswer(invocation -> invocation.getArgument(0));

    CreateOrderCommand command = new CreateOrderCommand(
      List.of(new OrderLine("SKU-001", 2)),
      new BigDecimal("49.90"), "EUR", "pm_test"
    );

    Order result = useCase.createOrder(command);

    var captor = ArgumentCaptor.forClass(Order.class);
    verify(orderRepositoryPort).save(captor.capture());

    Order savedOrder = captor.getValue();

    assertThat(savedOrder.getId()).isNotNull();
    assertThat(savedOrder.getStatus()).isEqualTo(OrderStatus.PENDING);
    assertThat(savedOrder.getTotalAmount()).isEqualByComparingTo("49.90");
    assertThat(savedOrder.getLines()).containsExactly(new OrderLine("SKU-001", 2));
    assertThat(result).isSameAs(savedOrder);
  }
}
