package com.devmeks.pangenerator.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.LuhnCheck;


/**
 * The type Pan.
 */
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Slf4j
@Getter
@Setter
@Builder
@Entity
@Table(name = "pans")
@AllArgsConstructor
@NoArgsConstructor
public class Pan {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false, updatable = false)
  private String id;
  @Column(name = "pan", unique = true, length = 19, nullable = false)
  @LuhnCheck
  private String cardNumber;


}
