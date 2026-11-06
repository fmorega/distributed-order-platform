package io.github.fmorega.order.infrastructure.driven.persistence.adapter;

import io.github.fmorega.order.application.model.OutboxMessage;
import io.github.fmorega.order.application.ports.driven.OutboxRepositoryPort;
import io.github.fmorega.order.infrastructure.driven.persistence.model.OutboxMessageMO;
import io.github.fmorega.order.infrastructure.driven.persistence.repository.OutboxMessageJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OutboxRepositoryAdapter implements OutboxRepositoryPort {

  private final OutboxMessageJpaRepository outboxMessageJpaRepository;

  @Override
  public void save(OutboxMessage message) {
    OutboxMessageMO mo = new OutboxMessageMO();
    mo.setId(message.id());
    mo.setAggregateId(message.aggregateId());
    mo.setSagaId(message.sagaId());
    mo.setCorrelationId(message.correlationId());
    mo.setCausationId(message.causationId());
    mo.setMessageType(message.messageType());
    mo.setDestination(message.destination());
    mo.setMessageKey(message.messageKey());
    mo.setVersion(message.version());
    mo.setPayload(message.payload());
    mo.setCreatedAt(message.createdAt());
    mo.setPublishedAt(null);
    mo.setAttempts(0);

    outboxMessageJpaRepository.save(mo);
  }

  @Override
  public List<OutboxMessage> findPendingForUpdate(int limit) {
    return outboxMessageJpaRepository.findPendingForUpdate(limit).stream()
      .map(mo -> new OutboxMessage(
        mo.getId(), mo.getAggregateId(), mo.getSagaId(), mo.getCorrelationId(),
        mo.getCausationId(), mo.getMessageType(), mo.getDestination(),
        mo.getMessageKey(), mo.getVersion(), mo.getPayload(), mo.getCreatedAt()))
      .toList();
  }

  @Override
  public void markPublished(UUID messageId, Instant publishedAt) {
    OutboxMessageMO mo = outboxMessageJpaRepository.findById(messageId).orElseThrow();
    mo.setPublishedAt(publishedAt);
  }

  @Override
  public void registerFailedAttempt(UUID messageId) {
    OutboxMessageMO mo = outboxMessageJpaRepository.findById(messageId).orElseThrow();
    mo.setAttempts(mo.getAttempts() + 1);
  }
}
