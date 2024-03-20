package com.devmeks.pangenerator.service;


import com.devmeks.pangenerator.dto.request.UserDto;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.model.User;
import com.devmeks.pangenerator.repository.UserRepo;
import com.devmeks.pangenerator.util.PanUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j

public class UserService {


  private final PanUtils panUtils;

  private final UserRepo userRepo;

  @Autowired
  public UserService(PanUtils panUtils, UserRepo userRepo) {

    this.userRepo = userRepo;
    this.panUtils = panUtils;
  }

  private static String getCleanErrorMessage(String errorMessage) {

    String extractedMessage = StringUtils.substringBetween(errorMessage, "Detail: ", "]");
    return extractedMessage.substring(extractedMessage.indexOf("=") + 1).trim();

  }

  public Mono<ResponseDto> createUser(UserDto userDto) {


    var user = User.builder()
        .email(userDto.getEmail())
        .passwordHash(panUtils.encryptPassword(userDto.getPassword()))
        .username(userDto.getUserName())
        .createdBy("SuperAdmin")
        .build();

    try {
      userRepo.save(user);

    } catch (DataIntegrityViolationException e) {

      var errorMessage = getCleanErrorMessage(e.getMessage());

      return panUtils.processException(e, errorMessage);
    }


    ResponseDto responseDto = new ResponseDto();
    log.info(userDto.toString());

    return Mono.just(responseDto);


  }


}
