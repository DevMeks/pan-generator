package com.devmeks.pangenerator.dto.request;


import com.devmeks.pangenerator.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
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
public class CreatePanFromMobileNumDto implements BaseDto {

  @JsonProperty("mobileNumber")
  @NotNull(message = "mobileNumber is mandatory")
  @NotEmpty(message = "mobileNumber should not be empty")
  @Pattern(regexp = "\\d{11}", message = "MobileNumber must be 11 digits long")
  @SchemaProperty(name = "mobileNumber", schema = @Schema(
      name = "mobileNumber",
      implementation = String.class))
  private String mobileNumber;

  @JsonProperty("cardScheme")
  @NotNull(message = "cardScheme is mandatory")
  @NotEmpty(message = "cardScheme should not be empty")
  @SchemaProperty(name = "cardScheme", schema = @Schema(
      name = "cardScheme",
      implementation = String.class))
  private String cardScheme;

}


