package com.cola.booking.query.application.kafka;

import static com.cola.booking.query.domain.AvailabilityStatusEnum.BOOKED;

import com.cola.booking.query.domain.AvailabilityStatusEnum;
import com.cola.booking.query.domain.BookingHistory;
import com.cola.booking.query.domain.BookingHistoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsummer {

  @Value("${topic.name")
  private String topicName;

  private BookingHistoryService bookingHistoryService;

  public KafkaConsummer(BookingHistoryService bookingHistoryService) {
    this.bookingHistoryService = bookingHistoryService;
  }

  @KafkaListener(topics = "${topic.name}", groupId = "group_id")
  public void consume(ConsumerRecord<String, String> payload) {
    log.info("Topic: {}", topicName);
    log.info("key: {}", payload.key());
    log.info("Headers: {}", payload.headers());
    log.info("Partition: {}", payload.partition());
    log.info("Order: {}", payload.value());
    BookingRecord bookingRecord = null;
    try {
      bookingRecord = new ObjectMapper().readValue(payload.value(), BookingRecord.class);
    } catch (JsonProcessingException e) {
      log.error(e.getMessage(), e);
    }
    Booking record = bookingRecord.getBooking();
    bookingHistoryService.save(
        BookingHistory.builder()
            .id(record.getId())
            .userId(record.getUserId())
            .roomNumber(record.getRoomNumber())
            .startDateTime(record.getStartDateTime())
            .status(
                "create".equals(bookingRecord.getType())
                    ? BOOKED.getValue()
                    : AvailabilityStatusEnum.FREE.getValue())
            .participants(String.join(",", record.getParticipants()))
            .build());
  }

  //  @KafkaListener(topics = "topicName", groupId = "group_id")
  //  public void listenWithHeaders(
  //      @Payload String message,
  //      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
  //    System.out.println(
  //        "Received Message: " + message
  //            + "from partition: " + partition);
  //  }
}
