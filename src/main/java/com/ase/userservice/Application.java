package com.ase.userservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
    info = @Info(
        title = "Student API",
        version = "1.0",
        description = "API documentation for managing students"
    )
)
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
