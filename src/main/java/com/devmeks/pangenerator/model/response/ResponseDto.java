package com.devmeks.pangenerator.model.response;


import com.devmeks.pangenerator.model.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseDto  implements BaseDto {

    @JsonProperty("pan")
    private String pan;

    @JsonProperty("error")
    private String error;

}
