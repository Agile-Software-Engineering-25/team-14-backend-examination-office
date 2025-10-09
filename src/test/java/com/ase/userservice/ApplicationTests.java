package com.ase.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
    properties = {
        "spring.security.oauth2.resourceserver.jwt.jwk-set-uri=http://localhost/fake-jwks"
    })
class ApplicationTests {

  @Test
  void contextLoads() {
  }
}
