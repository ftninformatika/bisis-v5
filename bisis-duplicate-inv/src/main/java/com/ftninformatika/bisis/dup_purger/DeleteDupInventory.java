package com.ftninformatika.bisis.dup_purger;


import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.controller.RecordsController;
import com.ftninformatika.bisis.rest_service.repository.mongo.interfaces.RecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.interfaces.ItemAvailabilityRepository;
import com.mongodb.client.MongoCursor;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.bson.Document;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

@ComponentScan("com.ftninformatika")
@EnableMongoRepositories(basePackages = "com.ftninformatika")
@PropertySource("classpath:config.properties")
public class DeleteDupInventory {



    public static void main(String[] args) {
        PropertyConfigurator.configure(
                DeleteDupInventory.class.getResourceAsStream("/log4j.properties"));
        Logger.getLogger(DeleteDupInventory.class).info("BISIS5 duplicates purger starting...");


        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(ch.qos.logback.classic.Level.INFO);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(LibraryPrefixProvider.class);
        ctx.register(DDConfigElastic.class);
        ctx.register(DDConfigMongo.class);
        ctx.register(RecordsController.class);
        ctx.refresh();

        DDConfigMongo conf = ctx.getBean(DDConfigMongo.class);

        System.out.println(conf.DEPARTMENT_REGEX);
        LibraryPrefixProvider libProvider = ctx.getBean(LibraryPrefixProvider.class);
        libProvider.setPrefix(conf.LIBRARY);

        RecordsController recordsController = ctx.getBean(RecordsController.class);
        RecordsRepository recordsRepository = ctx.getBean(RecordsRepository.class);
        ItemAvailabilityRepository itemAvailabilityRepository = ctx.getBean(ItemAvailabilityRepository.class);
        MongoTemplate mongoTemplate = ctx.getBean(MongoTemplate.class);

        System.out.println("Finding duplicates...");
        MongoCursor<Document> searchResults = mongoTemplate.getCollection("bgb_itemAvailability").find(new Document("ctlgNo",
                new Document("$regex", conf.DEPARTMENT_REGEX))).iterator();
        Set<String> dups = new HashSet<>();

        {
            Set<String> allCtlgs = new HashSet<>();
             while(searchResults.hasNext()) {
                String inv = (String) searchResults.next().get("ctlgNo");
                if (allCtlgs.contains(inv))
                    dups.add(inv);
                else
                    allCtlgs.add(inv);
             }
        }
        System.out.println("Duplicates found...\nStarting purging dupes...\nDuplicates count: " + dups.size());
        if (dups.size() == 0) {
            System.out.println("No duplicates found for query regex: " + conf.DEPARTMENT_REGEX);
            System.exit(0);
        }

        for (String inv: dups) {
            List<ItemAvailability> availabilities = itemAvailabilityRepository.findAllByCtlgNo(inv);

            if (availabilities.size() > 1) {

            List<ItemAvailability> changed = availabilities.stream().collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(ItemAvailability::getRecordID))),
                    ArrayList::new));

            availabilities.stream().forEach(
                    i -> {
                        if (!changed.contains(i))
                            itemAvailabilityRepository.delete(i);
                    }
            );

                List<Record> records = recordsRepository.getRecordsByPrimerakInvNum(inv);
                if (records != null) {
                    switch (records.size()) {
                        case 0: {
                            for (ItemAvailability i: availabilities)
                                itemAvailabilityRepository.delete(i);
                        } break;
                        case 1: {
                            for (ItemAvailability i: availabilities) {
                                if (records.get(0).getPrimerak(inv) != null && records.get(0).getRecordID() != Integer.valueOf(i.getRecordID()))
                                    itemAvailabilityRepository.delete(i);
                            }
                        } break;
                        case 2: {
                            Record r = DupInvs.getIvSubSetRec(records.get(0), records.get(1));
                            if (r != null) {
                                recordsController.deleteRecord(r.get_id());
                            }
                            else {
                                System.out.println("greska brisanje zapisa sa RN:" + r.getRN());
                            }
                        } break;
                        default: {
                            System.out.println("Vise od 3 zapisa!");
                        } break;
                    }
                }
                else {
                    for (ItemAvailability i: availabilities)
                        itemAvailabilityRepository.delete(i);
                }
            }
        }
        System.out.println("Done!");
    }
}
