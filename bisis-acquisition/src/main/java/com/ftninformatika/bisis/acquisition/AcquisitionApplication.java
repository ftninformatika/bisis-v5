package com.ftninformatika.bisis.acquisition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories("com.ftninformatika")
@SpringBootApplication
@ComponentScan("com.ftninformatika")
public class AcquisitionApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(AcquisitionApplication.class, args);
  }
}
