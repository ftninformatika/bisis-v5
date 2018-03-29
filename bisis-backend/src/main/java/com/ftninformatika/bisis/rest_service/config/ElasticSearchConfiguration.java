package com.ftninformatika.bisis.rest_service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by Petar on 7/5/2017.
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.ftninformatika")
@ComponentScan(basePackages = {"com.ftninformatika"})
public class ElasticSearchConfiguration {

   /* @Bean
    public NodeBuilder nodeBuilder() {
        return new NodeBuilder();
    }*/

 /*   @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        Settings.Builder elasticsearchSettings =
                Settings.settingsBuilder()
                        .put("http.enabled", "false") // 1
                        .put("path.data", /*tmpDir.toAbsolutePath().toString()*///"/elastic/storage") // 2
     /*                   .put("path.home", "PATH_TO_YOUR_ELASTICSEARCH_DIRECTORY"); // 3

        //logger.debug(tmpDir.toAbsolutePath().toString());
        return new ElasticsearchTemplate(nodeBuilder()
                .local(true)
                .settings(elasticsearchSettings.build())
                .node()
                .client());
    }*/

}
