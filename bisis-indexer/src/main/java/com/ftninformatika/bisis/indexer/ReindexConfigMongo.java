package com.ftninformatika.bisis.indexer;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Profile("index")
@Configuration
@EnableMongoRepositories("com.ftninformatika")
public class ReindexConfigMongo extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "bisis";
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoClient mongoClient = new MongoClient();
        return mongoClient;
    }
}