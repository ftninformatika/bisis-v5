package com.ftninformatika.bisis.rest_service.config;

import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;


/**
 * Created by Petar on 7/5/2017.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ftninformatika")
@ComponentScan(basePackages = "com.ftninformatika")
public class ElasticSearchConfiguration {


    @Bean
    public Client client() throws Exception {

        Settings esSettings = Settings.builder()
              //  .put("cluster.name", "bisis5")
                .put("client.transport.sniff", true)
                .build();

        TransportClient client = new PreBuiltTransportClient(esSettings);
        client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }


}
