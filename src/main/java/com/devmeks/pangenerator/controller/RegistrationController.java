package com.devmeks.pangenerator.controller;


import com.devmeks.pangenerator.dto.request.LoginUserDto;
import com.devmeks.pangenerator.dto.request.UserDto;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.service.UserService;
import com.devmeks.pangenerator.util.PanGeneratorUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/pan-generator/registration/users")
public class RegistrationController {

  UserService userService;
  PanGeneratorUtils panGeneratorUtils;

  @Autowired
  public RegistrationController(UserService userService, PanGeneratorUtils panGeneratorUtils) {
    this.userService = userService;
    this.panGeneratorUtils = panGeneratorUtils;
  }

  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Mono<ResponseDto>> createUser(@Valid @RequestBody UserDto user)  {

    return panGeneratorUtils.processResponse(userService.createUser(user));


  }

  @GetMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  public ResponseEntity<Mono<ResponseDto>> login(@Valid @RequestBody LoginUserDto user)  {

    return panGeneratorUtils.processResponse(userService.login(user));


  }

  @PatchMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE}, params = {"userName", "otp"})
  public ResponseEntity<Mono<ResponseDto>>  activateAccount(
      @RequestParam(value = "userName") String userName,
      @RequestParam(value = "otp") String otp
  ){


    return  panGeneratorUtils.processResponse(userService.verifyAccount(userName, otp));



  }
}
