package com.ftninformatika.bisis.indexer;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ComponentScan("com.ftninformatika")
public class ReindexRecords {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("index");
        ctx.getEnvironment().setDefaultProfiles("index");
        ctx.register(LibraryPrefixProvider.class);
        ctx.register(ReindexConfig.class);
        ctx.refresh();

        List<LibraryConfiguration> libconfigs = ctx.getBean(LibraryConfigurationRepository.class).findAll();
        LibraryPrefixProvider libProvider = ctx.getBean(LibraryPrefixProvider.class);
        RecordsRepository recordsRepository = ctx.getBean(RecordsRepository.class);
        ElasticRecordsRepository elasticRecordsRepository = ctx.getBean(ElasticRecordsRepository.class);
        ElasticsearchTemplate elasticsearchTemplate = ctx.getBean(ElasticsearchTemplate.class);

        for (LibraryConfiguration lc : libconfigs) {
            libProvider.setPrefix(lc.getLibraryName());
            elasticRecordsRepository.deleteAll();
            elasticsearchTemplate.createIndex(ElasticPrefixEntity.class);

            long num = recordsRepository.count();
            int count = 0;
            Pageable p = new PageRequest(0, 1000);
            Page<Record> lr = recordsRepository.findAll(p);

            while (lr.hasNext()) {
                List<ElasticPrefixEntity> ep = new ArrayList<>();
                for (Record rec : lr) {
                    Map<String, List<String>> prefixes = PrefixConverter.toMap(rec, null);
                    ElasticPrefixEntity ee = new ElasticPrefixEntity(rec.get_id(), prefixes);
                    ep.add(ee);
                }
                elasticRecordsRepository.save(ep);
                count += 1000;
                System.out.println("Processed " + count + " of " + num + " records! Library: " + lc.getLibraryName());
                lr = recordsRepository.findAll(lr.nextPageable());
            }

            // resto
            List<ElasticPrefixEntity> ep = new ArrayList<>();
            for (Record rec : lr) {
                Map<String, List<String>> prefixes = PrefixConverter.toMap(rec, null);
                ElasticPrefixEntity ee = new ElasticPrefixEntity(rec.get_id(), prefixes);
                ep.add(ee);
            }
            elasticRecordsRepository.save(ep);
            System.out.println("Processed " + num + " of " + num + " records! Library: " + lc.getLibraryName());
        }
        System.out.println("Finished indexing!!!");
    }

}
