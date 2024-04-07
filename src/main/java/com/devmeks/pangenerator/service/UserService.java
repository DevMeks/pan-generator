package com.devmeks.pangenerator.service;


import com.devmeks.pangenerator.dto.request.LoginUserDto;
import com.devmeks.pangenerator.dto.request.UserDto;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.exception.InvalidPasswordException;
import com.devmeks.pangenerator.exception.PasswordMismatchException;
import com.devmeks.pangenerator.model.Organization;
import com.devmeks.pangenerator.model.User;
import com.devmeks.pangenerator.repository.OrganizationRepo;
import com.devmeks.pangenerator.repository.UserRepo;
import com.devmeks.pangenerator.util.PanGeneratorUtils;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@Slf4j

public class UserService {


  private final PanGeneratorUtils panGeneratorUtils;

  private final UserRepo userRepo;

  private final OrganizationRepo organizationRepo;

  @Autowired
  public UserService(PanGeneratorUtils panGeneratorUtils, UserRepo userRepo, OrganizationRepo organizationRepo) {

    this.userRepo = userRepo;
    this.panGeneratorUtils = panGeneratorUtils;
    this.organizationRepo = organizationRepo;
  }

  private static String getCleanErrorMessage(String errorMessage) {

    String extractedMessage = StringUtils.substringBetween(errorMessage, "Detail: ", "]");
    return extractedMessage.substring(extractedMessage.indexOf("=") + 1).trim();

  }

  public Mono<ResponseDto> createUser(UserDto userDto) {

    //check if passwords provided matches
    if(!userDto.getPassword().equals(userDto.getConfirmPassword())){
      return panGeneratorUtils.processException(new PasswordMismatchException());

    }

    //check if organization exists and assign to retrievedOrganization
    var retrievedOrganization = organizationRepo.findOrganizationByName(userDto.getOrganizationName());

    if (Objects.isNull(retrievedOrganization)){
      var organization = Organization.builder()
          .name(userDto.getOrganizationName())
          .build();

      //create new Organization and assign to retrievedOrganization
      retrievedOrganization= organizationRepo.save(organization);


    }

    //generate otp
    var otp = PanGeneratorUtils.generateOTP(6);


    var user = User.builder()
        .email(userDto.getEmail())
        .passwordHash(panGeneratorUtils.encryptPassword(userDto.getPassword()))
        .username(userDto.getUserName())
        .organization(retrievedOrganization)
        .otp(otp.getCode())
        .otpExpiryDate(otp.getExpiryDateTime())
        .build();

    try {
      userRepo.save(user);

    } catch (DataIntegrityViolationException e) {

      var errorMessage = getCleanErrorMessage(e.getMessage());

      return panGeneratorUtils.processException(e, errorMessage);
    }


    ResponseDto responseDto = new ResponseDto();
    responseDto.setResponseStatus(ResponseStatus.SUCCESSFUL);
    responseDto.setResponseMessage("A verification email has been sent to your email address");

    log.info(userDto.toString());

    return Mono.just(responseDto);


  }

  public Mono<ResponseDto> login(LoginUserDto loginUserDto){

    //retrieve user
    var user = userRepo.findByUsername(loginUserDto.getUserName());

    //check that password is correct
    if ( !panGeneratorUtils.isPasswordValid(loginUserDto.getPassword(), user.getPassword())){
      return panGeneratorUtils.processException(new InvalidPasswordException());
    }


    return null;




  }


}
