package com.devmeks.pangenerator.controller;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RequestDto {

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("cardScheme")
    private String cardScheme;

}
