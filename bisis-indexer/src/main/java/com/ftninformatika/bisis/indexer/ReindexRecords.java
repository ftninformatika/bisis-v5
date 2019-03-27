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
import java.util.List;
import java.util.Map;

@ComponentScan("com.ftninformatika")
public class ReindexRecords {

    private static Logger log = Logger.getLogger(ReindexRecords.class);
    public static final String INDEX_SUFFIX = "library_domain";

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("enter library prefix, for example all (for all libraries), bgb, gbns, bs,..");
            return;
        }
        String library_prefix = args[0];

//        PropertyConfigurator.configure(
//                ReindexRecords.class.getResourceAsStream("/log4j.properties"));
//        Logger.getLogger(ReindexRecords.class).info("BISIS5 record indexer starting...");
//
//        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
//        root.setLevel(ch.qos.logback.classic.Level.INFO);


        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(LibraryPrefixProvider.class);
        ctx.register(ReindexConfigMongo.class);
        ctx.register(ReindexConfigElastic.class);
        ctx.refresh();

        List<LibraryConfiguration> libconfigs = ctx.getBean(LibraryConfigurationRepository.class).findAll();

        log.info("Starting indexing!");
        System.out.println("Starting indexing!");

        if (library_prefix.equalsIgnoreCase("all")) {
            for (LibraryConfiguration lc : libconfigs) {
                indexLibrary(lc.getLibraryName(), ctx);
            }
        } else {
            boolean found = false;
            for (LibraryConfiguration lc: libconfigs) {
                if (lc.getLibraryName().equalsIgnoreCase(library_prefix)) {
                    indexLibrary(library_prefix.toLowerCase(), ctx);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Library with that prefix does not exist!");
                return;
            }

        }

        System.out.println("Finished indexing!!!");
        log.info("Finished indexing!");
    }


    private static void indexLibrary(String libraryName, AnnotationConfigApplicationContext ctx) {
        LibraryPrefixProvider libProvider = ctx.getBean(LibraryPrefixProvider.class);
        libProvider.setPrefix(libraryName);
        RecordsRepository recordsRepository = ctx.getBean(RecordsRepository.class);
        ElasticRecordsRepository elasticRecordsRepository = ctx.getBean(ElasticRecordsRepository.class);
        ElasticsearchTemplate elasticsearchTemplate = ctx.getBean(ElasticsearchTemplate.class);

        try {
            elasticsearchTemplate.deleteIndex(libraryName + INDEX_SUFFIX);
            log.info("Deleted index for library: " + libraryName);
            elasticsearchTemplate.createIndex(ElasticPrefixEntity.class);
            log.info("Created index for library: " + libraryName);
            elasticsearchTemplate.putMapping(ElasticPrefixEntity.class);
        } catch (IndexNotFoundException e) {
            elasticsearchTemplate.createIndex(ElasticPrefixEntity.class);
            elasticsearchTemplate.putMapping(ElasticPrefixEntity.class);
            log.info("Created index for library: " + libraryName);
        }

        long num = recordsRepository.count();
        int count = 0;
        Pageable p = PageRequest.of(0, 1000);
        Page<Record> lr = recordsRepository.findAll(p);
        int pages = lr.getTotalPages();
        for (int i = 0; i < pages; i++) {
            List<ElasticPrefixEntity> ep = new ArrayList<>();
            for (Record rec : lr) {
                Map<String, List<String>> prefixes = PrefixConverter.toMap(rec, null);
                ElasticPrefixEntity ee = new ElasticPrefixEntity(rec.get_id(), prefixes);
                ep.add(ee);
            }
            elasticRecordsRepository.saveAll(ep);

            count += ep.size();
            System.out.println("Processed " + count + " of " + num + " records! Library: " + libraryName);
            log.info("Processed " + count + " of " + num + " records! Library: " + libraryName);
            if (!lr.isLast()) {
                p = lr.nextPageable();
                lr = recordsRepository.findAll(p);
            }
        }
        elasticsearchTemplate.getClient().admin().indices().prepareForceMerge(libraryName + INDEX_SUFFIX).setMaxNumSegments(1).execute().actionGet();
    }

}
