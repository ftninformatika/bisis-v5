package com.ftninformatika.bisis.indexer;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import com.ftninformatika.bisis.prefixes.PrefixConverter;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.elasticsearch.index.IndexNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ComponentScan("com.ftninformatika")
public class ReindexRecords {

    private static Logger log = Logger.getLogger(ReindexRecords.class);

    public static void main(String[] args) {

        PropertyConfigurator.configure(
        ReindexRecords.class.getResourceAsStream("/log4j.properties"));
        Logger.getLogger(ReindexRecords.class).info("BISIS5 record indexer starting...");


        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(ch.qos.logback.classic.Level.INFO);


        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("index");
        ctx.register(LibraryPrefixProvider.class);
        ctx.register(ReindexConfigMongo.class);
        ctx.register(ReindexConfigElastic.class);
        ctx.refresh();

        List<LibraryConfiguration> libconfigs = ctx.getBean(LibraryConfigurationRepository.class).findAll();
        List<String> libNames = libconfigs.stream().map(i -> i.getLibraryName()).collect(Collectors.toList());
        LibraryPrefixProvider libProvider = ctx.getBean(LibraryPrefixProvider.class);

        if (args.length == 1 && libNames.contains(args[0])) {
            log.info("Starting indexing for specific library: ");

        }

        log.info("Starting indexing all libraries!");
        libconfigs.sort(Comparator.comparing(LibraryConfiguration::getLibraryName));
        for (LibraryConfiguration lc : libconfigs) {
            libProvider.setPrefix(lc.getLibraryName());
            RecordsRepository recordsRepository = ctx.getBean(RecordsRepository.class);
            ElasticRecordsRepository elasticRecordsRepository = ctx.getBean(ElasticRecordsRepository.class);
            ElasticsearchTemplate elasticsearchTemplate = ctx.getBean(ElasticsearchTemplate.class);

            try {
                elasticsearchTemplate.deleteIndex(ElasticPrefixEntity.class);
                log.info("Deleted index for library: " + lc.getLibraryName());
                elasticsearchTemplate.createIndex(ElasticPrefixEntity.class);
                log.info("Created index for library: " + lc.getLibraryName());
                elasticsearchTemplate.putMapping(ElasticPrefixEntity.class);
            }
            catch (IndexNotFoundException e){
                elasticsearchTemplate.createIndex(ElasticPrefixEntity.class);
                elasticsearchTemplate.putMapping(ElasticPrefixEntity.class);
                log.info("Created index for library: " + lc.getLibraryName());
            }


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
                log.info("Processed " + count + " of " + num + " records! Library: " + lc.getLibraryName());
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
            log.info("Processed " + count + " of " + num + " records! Library: " + lc.getLibraryName());
        }
        System.out.println("Finished indexing!!!");
        log.info("Finished indexing!");
    }

}
