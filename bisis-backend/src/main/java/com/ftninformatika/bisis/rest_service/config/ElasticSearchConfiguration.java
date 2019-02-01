//package com.ftninformatika.bisis.rest_service.config;
//
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
//import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
//
//import java.net.InetAddress;
//
///**
// * Created by Petar on 7/5/2017.
// */
//@Configuration
//@EnableElasticsearchRepositories(basePackages = "com.ftninformatika")
//@ComponentScan(basePackages = {"com.ftninformatika"})
//public class ElasticSearchConfiguration {
//
//    @Bean
//    public Client client() throws Exception {
//
//        Settings esSettings = Settings.settingsBuilder()
//              //  .put("cluster.name", "bisis5")
//                .build();
//
//         Client client =TransportClient.builder()
//                .settings(esSettings)
//                .build()
//                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));
////                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.200.1"), 9300))
////                .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("192.168.200.2"), 9300));
//
//        return client;
//    }
//
//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
//        return new ElasticsearchTemplate(client());
//    }
//
//}
