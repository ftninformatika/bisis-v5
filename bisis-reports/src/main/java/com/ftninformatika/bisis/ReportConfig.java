package com.ftninformatika.bisis;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.mongodb.*;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

/**
 * Created by dboberic on 22/10/2017.
 */

@Profile("reporting")
@Configuration
@EnableMongoRepositories("com.ftninformatika")

public class ReportConfig extends AbstractMongoConfiguration {



   @Override
    protected String getDatabaseName() {
        return "10_11_bisis";
    }

    @Override
    public Mongo mongo() throws Exception {

          MongoClient mongoClient = new MongoClient();
        return mongoClient;
    }


}
