package com.devmeks.pangenerator.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The type Bin properties.
 */
@Configuration
@ConfigurationProperties("bin.scheme")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BinProperties {
  private String verve;
  private String mastercard;
}
