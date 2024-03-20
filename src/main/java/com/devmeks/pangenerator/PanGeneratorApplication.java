package com.devmeks.pangenerator;


import com.devmeks.pangenerator.config.RsaKeyProperties;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * The type Pan generator application.
 */
@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication()
@EnableEncryptableProperties
public class PanGeneratorApplication {

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {

    SpringApplication.run(PanGeneratorApplication.class, args);
  }

}
