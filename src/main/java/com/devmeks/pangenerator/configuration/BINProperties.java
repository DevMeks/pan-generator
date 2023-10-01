package com.devmeks.pangenerator.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("bin.scheme")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BINProperties {

    private String verve;
    private String mastercard;


}
