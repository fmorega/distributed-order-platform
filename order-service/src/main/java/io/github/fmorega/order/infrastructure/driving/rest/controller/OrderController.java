package io.github.fmorega.order.infrastructure.driving.rest.controller;

import io.github.fmorega.order.api.OrdersApi;
import io.github.fmorega.order.api.model.CreateOrderRequest;
import io.github.fmorega.order.api.model.OrderResponse;
import io.github.fmorega.order.application.ports.driving.CreateOrderServicePort;
import io.github.fmorega.order.application.ports.driving.GetOrderServicePort;
import io.github.fmorega.order.infrastructure.driving.rest.mapper.OrderApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OrderController implements OrdersApi {

  private final CreateOrderServicePort createOrderServicePort;
  private final GetOrderServicePort getOrderServicePort;
  private final OrderApiMapper orderApiMapper;

  @Override
  public ResponseEntity<OrderResponse> createOrder(CreateOrderRequest request) {
    var command = orderApiMapper.toCommand(request);
    var order = createOrderServicePort.createOrder(command);

    return ResponseEntity.created(URI.create("/api/v1/orders/" + order.getId()))
      .body(orderApiMapper.toResponse(order));
  }

  @Override
  public ResponseEntity<OrderResponse> getOrder(UUID orderId) {
    var order = getOrderServicePort.getOrder(orderId);
    return ResponseEntity.ok(orderApiMapper.toResponse(order));
  }
}
