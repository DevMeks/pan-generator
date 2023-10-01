package com.devmeks.pangenerator.repository;

import com.devmeks.pangenerator.model.PAN;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PANRepo extends JpaRepository<PAN, Long> {


}
