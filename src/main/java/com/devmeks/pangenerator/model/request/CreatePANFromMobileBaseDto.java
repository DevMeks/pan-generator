package com.devmeks.pangenerator.model.request;


import com.devmeks.pangenerator.model.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)

public class CreatePANFromMobileBaseDto implements BaseDto {

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("cardScheme")
    private String cardScheme;

}


