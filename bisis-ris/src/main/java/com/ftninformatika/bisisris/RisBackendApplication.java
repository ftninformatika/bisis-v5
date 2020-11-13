package com.ftninformatika.bisisris;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories("com.ftninformatika")
@SpringBootApplication
@ComponentScan("com.ftninformatika")

public class RisBackendApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {

        SpringApplication.run(RisBackendApplication.class, args);
    }
}
