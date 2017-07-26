package com.ftninformatika.bisis.rest_service;

import com.ftninformatika.bisis.librarian.Librarian;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;


@SpringBootApplication
@EnableMongoRepositories("com.ftninformatika")
@EnableElasticsearchRepositories("com.ftninformatika")
@ComponentScan("com.ftninformatika")
@EntityScan("com.ftninformatika")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


}
