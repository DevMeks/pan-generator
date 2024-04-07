package com.devmeks.pangenerator.model;



import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

@MappedSuperclass
@Slf4j
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractAuditingEntity implements Serializable {

  @Serial
  @Transient
  private static final long serialVersionUID = 2711428149016729163L;

  @Version
  @Column(name = "version", nullable = false)
  private Long version = 0L;

  @CreatedDate
  @Column(name = "createdAt", updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
  private LocalDateTime createdAt = LocalDateTime.now(ZoneId.of("Africa/Lagos"));

  @Column(name = "updatedBy", length = 64)
  private Long updatedBy;


  @UpdateTimestamp
  @Column(name = "updatedAt")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss")
  private LocalDateTime updatedAt ;



}
