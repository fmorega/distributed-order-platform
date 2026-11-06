package io.github.fmorega.order.infrastructure.driving.scheduling;

import io.github.fmorega.order.application.services.PublishOutboxUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxScheduler {

  private final PublishOutboxUseCase publishOutboxUseCase;

  @Value("${app.outbox.batch-size:5}")
  private int batchSize;

  @Scheduled(fixedDelayString = "${app.outbox.poll-delay-ms:5000}")
  public void publishPendingMessages() {
    publishOutboxUseCase.publishPending(batchSize);
  }
}
