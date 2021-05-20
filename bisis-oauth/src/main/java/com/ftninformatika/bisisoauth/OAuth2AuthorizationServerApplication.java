package com.ftninformatika.bisisoauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@EnableMongoRepositories("com.ftninformatika")
@ComponentScan("com.ftninformatika")
@SpringBootApplication
public class OAuth2AuthorizationServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(OAuth2AuthorizationServerApplication.class, args);
    }
}
