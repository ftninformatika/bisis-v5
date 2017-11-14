package com.ftninformatika.bisis.rest_service.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;


/**
 * Created by Petar on 11/10/2017.
 */
//@Configuration
//@EnableMongoRepositories(basePackages = "com.ftninformatika")
//@PropertySource("classpath:mongo.properties")
//@ContextConfiguration(initializers = YamlFileApplicationContextInitializer.class)
public class MongoConfig extends AbstractMongoConfiguration {

    @Autowired
    private Environment env;
    @Override
    protected String getDatabaseName() {
        return env.getProperty("spring.data.mongodb.database");
    }
    @Override
    public Mongo mongo() throws Exception {
        //return new MongoClient(env.getProperty("mongo.host"), Integer.parseInt(env.getProperty("mongo.port")));
        return new MongoClient(new MongoClientURI(env.getProperty("spring.data.mongodb.uri")));
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.thomasvitale.model";
    }
}
