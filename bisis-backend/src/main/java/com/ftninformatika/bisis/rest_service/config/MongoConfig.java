package com.ftninformatika.bisis.rest_service.config;

/**
 * Created by Petar on 8/8/2017.
 */
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.gson.JsonObject;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

//@Configuration
//@EnableMongoRepositories(basePackages = "com.ftninformatika")
//@ComponentScan(basePackages = {"com.ftninformatika"})
//@ContextConfiguration(initializers = YamlFileApplicationContextInitializer.class)
public class MongoConfig extends AbstractMongoConfiguration {

    @Value("${spring.data.mongodb.host}")
    private String host;

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Value("${spring.data.mongodb.port}")
    private int port;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    public Mongo mongo() throws Exception {
        ServerAddress serverAddress = new ServerAddress(host, port);
        /*List<MongoCredential> credentials = Collections.singletonList(
                MongoCredential.createCredential(user, databaseName, passwd.toCharArray())
        );*/

        MongoClient mongoClient = new MongoClient(/*serverAddress, credentials*/);
        return mongoClient;
    }

    @Override
    public MappingMongoConverter mappingMongoConverter() throws Exception {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(mongoDbFactory());
        ObjectMapper mapper = new ObjectMapper()
                .configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
                /*.registerModule(new SimpleModule() {
                    {
                        addDeserializer(ObjectId.class, new JsonDeserializer<ObjectId>() {
                            @Override
                            public ObjectId deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
                                TreeNode oid = p.readValueAsTree().get("$oid");
                                String string = oid.toString().replaceAll("\"", "");

                                return new ObjectId(string);
                            }
                        });
                    }
                });*/


        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, mongoMappingContext()) {
         /*   @Override
            public <S> S read(Class<S> clazz, DBObject dbo) {
                String string = JSON.serialize(dbo);
                try {
                    return mapper.readValue(string, clazz);
                } catch (IOException e) {
                    throw new RuntimeException(string, e);
                }
            }

            @Override
            public void write(Object obj, DBObject dbo) {
                String string = null;
                try {
                    string = mapper.writeValueAsString(obj);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(string, e);
                }
                dbo.putAll((DBObject) JSON.parse(string));
            }*/
        };

        return converter;
    }


    @Bean
    @NotNull
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}