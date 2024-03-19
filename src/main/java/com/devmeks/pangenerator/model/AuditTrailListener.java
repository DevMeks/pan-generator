package com.devmeks.pangenerator.model;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;


@Slf4j
public class AuditTrailListener {

  @PrePersist
  private void beforeAnyCreation(User user) {

    log.info("[USER AUDIT] About to add a user");
    user.setCreatedAt(LocalDateTime.now());

  }

  @PreUpdate
  private void beforeAnyUpdate(User user) {
    log.info("[USER AUDIT] About to update user: " + user.getId());
    user.setModified(LocalDateTime.now());
  }


}