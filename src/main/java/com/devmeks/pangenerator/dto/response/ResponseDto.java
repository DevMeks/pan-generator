package com.devmeks.pangenerator.dto.response;


import com.devmeks.pangenerator.dto.BaseDto;
import com.devmeks.pangenerator.exception.model.ApiError;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

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
  private String pan;

  @JsonProperty("responseStatus")
  private ResponseStatus responseStatus;

  @JsonProperty("error")
  private ApiError error;

}
