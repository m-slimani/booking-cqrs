package com.cola.booking.query.infrastructure.opening;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "opening")
@Getter
@Setter
public class OpeningProperties {

  private String startTime;
  private String endTime;
  private int duration;
}
