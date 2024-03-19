package com.devmeks.pangenerator.controller;


import com.devmeks.pangenerator.dto.request.UserDto;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.service.UserService;
import com.devmeks.pangenerator.util.PanUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/pan-generator/registration/users")
public class RegistrationController {

  UserService userService;
  PanUtils panUtils;

  @Autowired
  public RegistrationController(UserService userService, PanUtils panUtils) {
    this.userService = userService;
    this.panUtils = panUtils;
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Mono<ResponseDto>> createUser(@Valid @RequestBody UserDto user) {

    return panUtils.processResponse(userService.createUser(user));


  }
}
