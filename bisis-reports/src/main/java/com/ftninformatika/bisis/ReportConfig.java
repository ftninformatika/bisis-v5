package com.ftninformatika.bisis;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by dboberic on 22/10/2017.
 */

@Configuration
@EnableMongoRepositories("com.ftninformatika")
@PropertySource("classpath:config.properties")
public class ReportConfig extends AbstractMongoClientConfiguration {

  @Value("${spring.data.mongodb.uri}")
  private String mongoUri;

  @Value("${spring.data.mongodb.database}")
  private String databaseName;

  @Override
  protected String getDatabaseName() {
    return databaseName;
  }

  @Override
  public MongoClient mongoClient() {
    return MongoClients.create(mongoUri);
  }
}