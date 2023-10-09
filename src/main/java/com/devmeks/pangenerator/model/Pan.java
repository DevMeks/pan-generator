package com.devmeks.pangenerator.model;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;


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
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false)
  private Long id;
  @Column(name = "pan", unique = true, length = 16, nullable = false)
  private String cardNumber;

}
