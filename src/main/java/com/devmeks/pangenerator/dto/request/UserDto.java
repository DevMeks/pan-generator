package com.devmeks.pangenerator.dto.request;

import com.devmeks.pangenerator.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;


@Getter
public class UserDto implements BaseDto {

  @NotEmpty(message = "Please provide a valid username")
  @JsonProperty("username")
  private String userName;
  @Email(message = "Please provide a valid email address")
  @JsonProperty("email")
  private String email;

  @NotBlank(message = "Password cannot be blank")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
      message = "Password must be at least 8 characters and include at least one lowercase letter, one uppercase " +
          "letter, one digit, and one special character")
  @JsonProperty("password")
  private String password;

  @NotBlank(message = "Password cannot be blank")
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
      message = "This must match the password")
  @JsonProperty("confirmPassword")
  private String confirmPassword;


}
