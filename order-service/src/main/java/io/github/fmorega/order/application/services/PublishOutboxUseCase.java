package io.github.fmorega.order.application.services;

import io.github.fmorega.order.application.model.OutboxMessage;
import io.github.fmorega.order.application.ports.driven.MessagePublisherPort;
import io.github.fmorega.order.application.ports.driven.OutboxRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublishOutboxUseCase {

  private final OutboxRepositoryPort outboxRepositoryPort;
  private final MessagePublisherPort messagePublisherPort;

  @Transactional
  public void publishPending(int batchSize) {
    var messages = outboxRepositoryPort.findPendingForUpdate(batchSize);

    for (OutboxMessage message : messages) {
      try {
        messagePublisherPort.publish(message);
        outboxRepositoryPort.markPublished(message.id(), Instant.now());

        log.info("Outbox publication confirmed: messageId={}, type={}",
          message.id(), message.messageType());

      } catch (RuntimeException ex) {
        outboxRepositoryPort.registerFailedAttempt(message.id());

        log.warn("Outbox publication failed: messageId={}, reason={}",
          message.id(), ex.toString());

        if (Thread.currentThread().isInterrupted()) break;
      }
    }
  }
}
