package io.github.fmorega.order.application.ports.driven;

import io.github.fmorega.order.application.model.OutboxMessage;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface OutboxRepositoryPort {

  void save(OutboxMessage message);

  List<OutboxMessage> findPendingForUpdate(int limit);

  void markPublished(UUID messageId, Instant publishedAt);

  void registerFailedAttempt(UUID messageId);
}
