package com.ftninformatika.bisis.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories("com.ftninformatika")
@EnableElasticsearchRepositories("com.ftninformatika")
@ComponentScan("com.ftninformatika")
@SpringBootApplication
public class InventoryApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(InventoryApp.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(InventoryApp.class, args);
    }

}