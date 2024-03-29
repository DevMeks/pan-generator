package com.devmeks.pangenerator.repository;

import com.devmeks.pangenerator.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepo extends JpaRepository<Organization, Long> {

  Organization findOrganizationByName(String name);
}