package com.ftninformatika.bisis.rest_service.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

@Configuration
@EnableConfigurationProperties
public class MongoTransactionalConfiguration extends AbstractMongoConfiguration {

    @Autowired private Environment environment;

    @Bean
    MongoTransactionManager transactionManager(MongoDbFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

    @Bean
    public GridFsTemplate gridFsTemplate() throws Exception {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter());
    }

    @Bean
    @Override
    public MongoClient mongoClient() {
//        MongoClientURI dbURI = new MongoClientURI(environment.getProperty("spring.data.mongodb.uri"));
//        MongoClient client = new MongoClient(dbURI);
//        return client;
        return new MongoClient();
    }

    @Override
    protected String getDatabaseName() {
//        return environment.getProperty("spring.data.mongodb.database");
        return "bisis";
    }
}
