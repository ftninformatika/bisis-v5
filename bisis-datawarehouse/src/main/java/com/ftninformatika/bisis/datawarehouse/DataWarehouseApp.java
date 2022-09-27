package com.ftninformatika.bisis.datawarehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan("com.ftninformatika")
@EnableMongoRepositories("com.ftninformatika")
@SpringBootApplication
public class DataWarehouseApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(DataWarehouseApp.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(DataWarehouseApp.class, args);
    }

}
