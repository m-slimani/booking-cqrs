package com.cola.booking.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class BookingQueryApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookingQueryApplication.class, args);
  }
}
