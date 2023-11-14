package com.ftninformatika.bisis.books_common_merger;

import com.ftninformatika.bisis.config.MongoTransactionalConfiguration;
import com.ftninformatika.bisis.config.YAMLConfig;
import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.opac.books.BookCommon;
import com.ftninformatika.bisis.opac.controller.BookCommonController;
import com.ftninformatika.bisis.opac.controller.BookCoverController;
import com.ftninformatika.bisis.rest_service.controller.core.RecordsController;
import com.ftninformatika.bisis.rest_service.repository.elastic.ElasticRecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.BookCommonRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static com.ftninformatika.bisis.books_common_merger.BooksCommonMergerUtils.getCoverMultipart;

@ComponentScan("com.ftninformatika")
public class BooksCommonMerger {
    private static Logger log = LoggerFactory.getLogger(BooksCommonMerger.class);

    public static void main(String[] args) {
        if (args.length != 2 || (!args[0].equals("m") && !args[0].equals("i"))) {
            System.out.println("Please enter arguments, if [1]:mode, [2]if mode == i -> path do dir, if mode = m then library suffix!");
            System.exit(0);
        }
        String mode = args[0];
        String path = args[1];

        PropertyConfigurator.configure(BooksCommonMerger.class.getResourceAsStream("/log4j.properties"));
        LoggerFactory.getLogger(BooksCommonMerger.class).info("\n\n###STARTING\nBISIS5 merging book covers and synopsis via isbn starting...");

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(LibraryPrefixProvider.class);
        ctx.register(MongoTransactionalConfiguration.class);
        ctx.register(LibraryConfigurationRepository.class);
        ctx.register(CommonMergerConfigMongo.class);
        ctx.register(YAMLConfig.class);
        ctx.register(CommonMergerConfigElastic.class);
        ctx.register(BookCommonRepository.class);
        ctx.register(ElasticRecordsRepository.class);
        ctx.refresh();
        ctx.scan("com.ftninformatika");
        RecordsPair recordsPair = new RecordsPair();
        recordsPair.setBookCommonController(ctx.getBean(BookCommonController.class));
        recordsPair.setBookCoverController(ctx.getBean(BookCoverController.class));
        recordsPair.setRecordsController(ctx.getBean(RecordsController.class));
        recordsPair.setRecordsRepository(ctx.getBean(RecordsRepository.class));
        recordsPair.setElasticRecordsRepository(ctx.getBean(ElasticRecordsRepository.class));
        recordsPair.setLibraryConfigurationRepository(ctx.getBean(LibraryConfigurationRepository.class));
        if (mode.equals("m")) {
            List<String> selectedLibs =new ArrayList<>(Arrays.asList(path.split(",")));
            Pageable p = PageRequest.of(0, 1000);
            Page<BookCommon> bookCommonsPage = ctx.getBean(BookCommonRepository.class).findAll(p);
            int pageCount = bookCommonsPage.getTotalPages();
            for (int i = 0; i < pageCount; i++) {
                try {
                    recordsPair.pairBookCommonWithSelectedLib(bookCommonsPage, selectedLibs);
                    log.info("Book merger finished " + i +"/"+pageCount);
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
                if (!bookCommonsPage.isLast()) {
                    p = bookCommonsPage.nextPageable();
                    bookCommonsPage = ctx.getBean(BookCommonRepository.class).findAll(p);
                }
            }
            log.info("Book merger finished! ");

        } else {
            try (Stream<Path> walk = Files.walk(Paths.get(path))) {
                List<String> files = walk.filter(Files::isRegularFile).map(Objects::toString).collect(Collectors.toList());
                for (String fileName : files) {
                    if (!fileName.endsWith(".json")) continue;

                    BookCommon bookCommon = BooksCommonMergerUtils.getBookCommonFromPath(fileName);
                    if (bookCommon == null) {
                        log.warn("Cannot make BookCommon from path: " + fileName);
                        continue;
                    }
                    recordsPair.pairBookCommon(Arrays.asList(bookCommon));
                    recordsPair.getBookCommonController().saveModifyBookCommon(bookCommon).getStatusCode().equals(HttpStatus.OK);
                    BooksCommonMergerUtils.UID_COUNTER++;

                    if (!(BooksCommonMergerUtils.bookCoverValid(fileName))) {
                        log.info("No cover image for file: " + fileName);
                        System.out.println("No cover image for file: " + fileName);
                        continue;
                    }
                    MultipartFile coverMultipart = getCoverMultipart(fileName, files);
                    if (!recordsPair.getBookCoverController().uploadImage(bookCommon.getUid(), coverMultipart).getStatusCode().equals(HttpStatus.OK)) {
                        log.info("No cover image for file: " + fileName);
                    } else {
                        log.info("Saved image for file: " + fileName);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
