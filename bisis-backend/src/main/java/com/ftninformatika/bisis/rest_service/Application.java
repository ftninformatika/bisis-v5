package com.ftninformatika.bisis.rest_service;

import it.ozimov.springboot.mail.configuration.EnableEmailTools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Profile;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Profile("development")
@EnableMongoRepositories("com.ftninformatika")
@EnableElasticsearchRepositories("com.ftninformatika")
@EntityScan("com.ftninformatika")
@SpringBootApplication
@EnableEmailTools
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
