package com.ftninformatika.bisis;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by dboberic on 22/10/2017.
 */

@Profile("reporting")
@Configuration
@EnableMongoRepositories("com.ftninformatika")

public class ReportConfig extends AbstractMongoConfiguration {

  @Override
  protected String getDatabaseName() {
    return "bisis";
  }

//  @Override
//  public Mongo mongo() throws Exception {
//    MongoClient mongoClient = new MongoClient();
//    return mongoClient;
//  }

  @Override
  public MongoClient mongoClient() {
    return null;
  }
}
