package io.github.fmorega.order.application.ports.driven;

import io.github.fmorega.order.application.model.OutboxMessage;

public interface OutboxRepositoryPort {

  void save(OutboxMessage message);
}
