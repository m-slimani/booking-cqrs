package com.cola.booking.command.infrastructure.kafka;

import com.cola.booking.command.domain.event.BookingEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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

  public void send(BookingEvent bookingEvent) {
    String message = null;
    try {
      message = new ObjectMapper().writeValueAsString(bookingEvent);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    }
    log.info("Payload sent: {}", message);
    kafkaTemplate.send(topicName, message);
  }
}
