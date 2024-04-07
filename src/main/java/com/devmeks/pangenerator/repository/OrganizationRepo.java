package com.devmeks.pangenerator.repository;

import com.devmeks.pangenerator.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface OrganizationRepo extends JpaRepository<Organization, UUID> {

  Organization findOrganizationByName(String name);
}