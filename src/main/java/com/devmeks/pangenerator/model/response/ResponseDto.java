package com.devmeks.pangenerator.model.response;


import com.devmeks.pangenerator.model.BaseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ResponseDto  implements BaseDto {

    @JsonProperty("pan")
    private String pan;

    @JsonProperty("error")
    private APIError error;

}
