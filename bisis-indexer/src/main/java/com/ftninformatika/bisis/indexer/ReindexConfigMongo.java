package com.ftninformatika.bisis.indexer;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("com.ftninformatika")
@PropertySource("classpath:config.properties")
public class ReindexConfigMongo extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Override
    protected String getDatabaseName() {
        return "bisis";
    }

    @Override
    public MongoClient mongoClient() {
        System.out.println(mongoUri);
        return MongoClients.create(mongoUri);
    }
}
