package io.github.fmorega.order.application.ports.driven;

import io.github.fmorega.order.application.model.OutboxMessage;

public interface MessagePublisherPort {

  void publish(OutboxMessage message);
}
