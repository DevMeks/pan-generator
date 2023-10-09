package com.devmeks.pangenerator.dto.request;


import com.devmeks.pangenerator.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Create pan from mobile num dto.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
//@ApiModel(description = "Request object for generating PAN using mobile number and card scheme")
public class CreatePanFromMobileNumDto implements BaseDto {

  @JsonProperty("mobileNumber")
  @NotNull(message = "mobileNumber is mandatory")
  @NotEmpty(message = "mobileNumber should not be empty")
  @Pattern(regexp = "\\d{11}", message = "MobileNumber must be 11 digits long")
//  @ApiModelProperty(
//      value = "Mobile number of customer",
//      name = "mobileNumber",
//      dataType = "String",
//      example = "070XXXXXXXX")
  private String mobileNumber;

  @JsonProperty("cardScheme")
  @NotNull(message = "cardScheme is mandatory")
  @NotEmpty(message = "cardScheme should not be empty")
//  @ApiModelProperty(
//      value = "Card scheme of Pan",
//      name = "cardScheme",
//      dataType = "String",
//      example = "Verve")
  private String cardScheme;

}


