package com.cola.booking.command.infrastructure.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducer {

  @Value("${topic.name}")
  private String topicName;

  private final KafkaTemplate<String, String> kafkaTemplate;

  public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  public void send(Long bookingId, String message) {
    log.info("Payload sent: {}", message);
    ProducerRecord producerRecord = new ProducerRecord<>(topicName, null, String.valueOf(bookingId), message);
    kafkaTemplate.send(producerRecord);
  }
}
