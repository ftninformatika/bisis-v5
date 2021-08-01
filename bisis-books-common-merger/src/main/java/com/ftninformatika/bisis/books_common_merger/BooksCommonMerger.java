package com.ftninformatika.bisis.books_common_merger;

import com.ftninformatika.bisis.core.repositories.LibraryConfigurationRepository;
import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.opac.books.BookCommon;
import com.ftninformatika.bisis.rest_service.config.MongoTransactionalConfiguration;
import com.ftninformatika.bisis.rest_service.config.YAMLConfig;
import com.ftninformatika.bisis.rest_service.controller.core.RecordsController;
import com.ftninformatika.bisis.opac.controller.BookCommonController;
import com.ftninformatika.bisis.opac.controller.BookCoverController;
import com.ftninformatika.utils.LibraryPrefixProvider;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ftninformatika.bisis.books_common_merger.BooksCommonMergerUtils.getCoverMultipart;
/**
 * @author badf00d21  25.9.19.
 */
@ComponentScan("com.ftninformatika")
public class BooksCommonMerger {
    private static Logger log = Logger.getLogger(BooksCommonMerger.class);

    public static void main(String[] args) {
        if (args.length != 2 || (!args[0].equals("m") && !args[0].equals("i"))) {
            System.out.println("Please enter arguments, if [1]:mode, [2]if mode == i -> path do dir, if mode = m then library suffix!");
            System.exit(0);
        }
        String mode = args[0];
        String path = args[1];

        PropertyConfigurator.configure(
                BooksCommonMerger.class.getResourceAsStream("/log4j.properties"));
        Logger.getLogger(BooksCommonMerger.class).info("\n\n###STARTING\nBISIS5 merging book covers and sinopsis via isbn starting...");

        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(ch.qos.logback.classic.Level.INFO);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("production");
        ctx.register(LibraryPrefixProvider.class);
        ctx.register(MongoTransactionalConfiguration.class);
        ctx.register(LibraryConfigurationRepository.class);
        ctx.register(CommonMergerConfigMongo.class);
        ctx.register(YAMLConfig.class);
        ctx.register(CommonMergerConfigElastic.class);
        ctx.refresh();
        ctx.scan("com.ftninformatika");
        RecordsPair recordsPair = new RecordsPair();
        recordsPair.setBookCommonController(ctx.getBean(BookCommonController.class));
        recordsPair.setBookCoverController(ctx.getBean(BookCoverController.class));
        recordsPair.setRecordsController(ctx.getBean(RecordsController.class));
        recordsPair.setRecordsRepository(ctx.getBean(RecordsRepository.class));

        if (mode.equals("m")) {
            String[] selectedLibs = {path};
            int sucessCnt = 0;
            int bcId = 1;
            while (bcId != 0) {
                try {
                    BookCommon bc = recordsPair.getBookCommonController().getBookCommon(bcId).getBody();
                    if (bc == null) {
                        bcId = 0;
                        continue;
                    }
                    if (!recordsPair.pairBookCommonWithSelectedLib(bc, selectedLibs)) {
                        System.out.println("Coulnd pair book common: " + bc.getUid() + " for lib: " + path);
                        log.warn("Coulnd pair book common: " + bc.getUid() + " for lib: " + path);
                    } else {
                        System.out.println("Paired book common " + bc.getUid() + " for lib: " + path);
                        log.info("Paired book common " + bc.getUid() + " for lib: " + path);
                        sucessCnt++;
                    }
                    bcId++;
                }
                catch (Exception e) {
                    e.printStackTrace();
                    bcId = 0;
                }
            }
            log.info("Merged " + sucessCnt + " book common objects with " + path + "libraries!");
            System.out.println("Merged " + sucessCnt + " book common objects with " + path + "libraries!");
        }
        else {
            try (Stream<Path> walk = Files.walk(Paths.get(path))) {
                List<String> files = walk.filter(Files::isRegularFile).map(Objects::toString).collect(Collectors.toList());
                for (String fileName : files) {
                    if (!fileName.endsWith(".json")) continue;

                    BookCommon bookCommon = BooksCommonMergerUtils.getBookCommonFromPath(fileName);
                    if (bookCommon == null) {
                        log.warn("Cannot make BookCommon from path: " + fileName);
//                    System.out.println("Cannot make BookCommon from path: " + fileName);
                        continue;
                    }
                    if (!recordsPair.pairBookCommon(bookCommon)) continue;
                    if (!recordsPair.getBookCommonController().saveModifyBookCommon(bookCommon)
                            .getStatusCode().equals(HttpStatus.OK)) {
                        log.error("BookCommon: " + bookCommon.getIsbn() + " is not saved");
//                    System.out.println("BookCommon: " + bookCommon.getIsbn() + " is not saved");
                    }
                    BooksCommonMergerUtils.UID_COUNTER++;

                    if (!(BooksCommonMergerUtils.bookCoverValid(fileName))) {
                        log.info("No cover image for file: " + fileName);
                        System.out.println("No cover image for file: " + fileName);
                        continue;
                    }
                    MultipartFile coverMultipart = getCoverMultipart(fileName, files);
                    if (!recordsPair.getBookCoverController().uploadImage(bookCommon.getUid(), coverMultipart).getStatusCode().equals(HttpStatus.OK)) {
                        log.info("No cover image for file: " + fileName);
//                    System.out.println("No cover image for file: " + fileName);
                    } else {
                        log.info("Saved image for file: " + fileName);
//                    System.out.println("Saved image for file: " + fileName);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
