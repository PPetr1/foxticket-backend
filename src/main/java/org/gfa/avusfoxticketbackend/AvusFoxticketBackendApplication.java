package org.gfa.avusfoxticketbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@ComponentScan({ "org.gfa.avusfoxticketbackend.*" })
public class AvusFoxticketBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(AvusFoxticketBackendApplication.class, args);
  }
}
