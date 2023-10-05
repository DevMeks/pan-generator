package com.devmeks.pangenerator.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class APIError {
    private String errorMessage;


    //factory method
    public static APIError ceateAPIError(){
        return new APIError();
    }
}
