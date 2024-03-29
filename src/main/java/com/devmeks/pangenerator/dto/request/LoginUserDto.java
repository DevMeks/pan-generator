package com.devmeks.pangenerator.dto.request;

import com.devmeks.pangenerator.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;


@Getter
public class LoginUserDto implements BaseDto {

  @NotEmpty(message = "Please provide a valid username")
  @JsonProperty("username")
  private String userName;


  @NotBlank(message = "Password cannot be blank")
  @JsonProperty("password")
  private String password;


}
