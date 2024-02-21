package com.devmeks.pangenerator.repository;

import com.devmeks.pangenerator.model.Pan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Pan repo.
 */
@Repository
public interface PanRepo extends JpaRepository<Pan, String> {




}
