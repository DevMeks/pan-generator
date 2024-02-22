package com.devmeks.pangenerator;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The type Pan generator application.
 */
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
