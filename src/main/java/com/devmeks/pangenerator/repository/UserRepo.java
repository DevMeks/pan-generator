package com.devmeks.pangenerator.repository;

import com.devmeks.pangenerator.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface UserRepo extends JpaRepository<User, UUID> {

  User findByUsername(String username);


}