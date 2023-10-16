package com.devmeks.pangenerator.dto.response;


import com.devmeks.pangenerator.dto.BaseDto;
import com.devmeks.pangenerator.exception.model.ApiError;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.*;

/**
 * The type Response dto.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class ResponseDto implements BaseDto {

  @JsonProperty("pan")
  @SchemaProperty(name = "pan", schema = @Schema(
      name = "pan",
      maxLength = 16,
      implementation = String.class))
  private String pan;

  @JsonProperty("responseStatus")
  @SchemaProperty(name = "responseStatus", schema = @Schema(implementation = ResponseStatus.class))
  private ResponseStatus responseStatus;

  @JsonProperty("error")
  private ApiError error;

}
