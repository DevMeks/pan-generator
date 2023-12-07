package com.devmeks.pangenerator.dto.request;


import com.devmeks.pangenerator.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreatePanDto implements BaseDto {


  @JsonProperty("cardScheme")
  @NotNull(message = "cardScheme is mandatory")
  @NotEmpty(message = "cardScheme should not be empty")
  @SchemaProperty(name = "cardScheme", schema = @Schema(
      name = "cardScheme",
      implementation = String.class))
  private String cardScheme;

  @JsonProperty("isGlobalVerveCard")
  @SchemaProperty(name = "isGlobalVerveCard", schema = @Schema(
      name = "isGlobalVerveCard",
      implementation = Boolean.class))
  private boolean isGlobalVerveCard;


}


