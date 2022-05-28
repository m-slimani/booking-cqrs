package com.cola.booking.query.infrastructure.slot;

import java.time.LocalTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SLOT")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SlotEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "ROOM_NUMBER", nullable = false)
  private String roomNumber;

  @Column(name = "SLOT_NUMBER", nullable = false)
  private Integer slotNumber;

  @Column(name = "START_TIME", nullable = false)
  private LocalTime startTime;

  @Setter
  @Column(name = "STATUS")
  private String status;
}
