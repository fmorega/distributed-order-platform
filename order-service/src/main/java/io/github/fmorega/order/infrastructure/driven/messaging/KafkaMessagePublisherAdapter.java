package io.github.fmorega.order.infrastructure.driven.messaging;

import io.github.fmorega.order.application.model.OutboxMessage;
import io.github.fmorega.order.application.ports.driven.MessagePublisherPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMessagePublisherAdapter implements MessagePublisherPort {

  private final KafkaTemplate<String, String> kafkaTemplate;
  private final JsonMapper jsonMapper;

  @Override
  public void publish(OutboxMessage message) {
    Map<String, Object> envelope = new LinkedHashMap<>();
    envelope.put("messageId", message.id().toString());
    envelope.put("messageType", message.messageType());
    envelope.put("aggregateId", message.aggregateId().toString());
    envelope.put("sagaId", message.sagaId() == null ? null : message.sagaId().toString());
    envelope.put("correlationId", message.correlationId().toString());
    envelope.put("causationId", message.causationId() == null ? null : message.causationId().toString());
    envelope.put("version", message.version());
    envelope.put("occurredAt", message.createdAt().toString());
    envelope.put("payload", message.payload());

    String json = jsonMapper.writeValueAsString(envelope);

    try {
      var result = kafkaTemplate.send(message.destination(), message.messageKey(), json)
        .get(10, TimeUnit.SECONDS);

      log.info("Kafka ACK: messageId={}, topic={}, partition={}, offset={}",
        message.id(), message.destination(),
        result.getRecordMetadata().partition(), result.getRecordMetadata().offset());

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new IllegalStateException("Kafka publication interrupted: " + message.id(), e);

    } catch (ExecutionException | TimeoutException e) {
      throw new IllegalStateException("Kafka publication failed: " + message.id(), e);
    }
  }
}
