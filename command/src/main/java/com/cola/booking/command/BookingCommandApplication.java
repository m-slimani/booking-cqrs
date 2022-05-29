package com.cola.booking.command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class BookingCommandApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookingCommandApplication.class, args);
  }

}
