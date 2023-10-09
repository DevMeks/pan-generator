package com.devmeks.pangenerator.dto.request;


import com.devmeks.pangenerator.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


/**
 * The type Create pan from mobile num dto.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class CreatePanFromMobileNumDto implements BaseDto {

  @JsonProperty("mobileNumber")
  @NotNull(message = "mobileNumber is mandatory")
  @NotEmpty(message = "mobileNumber should not be empty")
  @Pattern(regexp = "\\d{11}", message = "MobileNumber must be 11 digits long")
  private String mobileNumber;

  @JsonProperty("cardScheme")
  @NotNull(message = "cardScheme is mandatory")
  @NotEmpty(message = "cardScheme should not be empty")
  private String cardScheme;

}


