package com.cola.booking.query.infrastructure.init;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "room")
@Getter
@Setter
public class RoomProperties {

  private String cokeNames;
  private String pepsiNames;

}
