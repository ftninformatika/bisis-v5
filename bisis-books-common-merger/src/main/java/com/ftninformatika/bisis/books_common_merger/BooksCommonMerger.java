package com.ftninformatika.bisis.books_common_merger;

import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author badf00d21  25.9.19.
 */
@ComponentScan("com.ftninformatika")
public class BooksCommonMerger {

    private static Logger log = Logger.getLogger(BooksCommonMerger.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please enter path to directory with files as argument!");
            System.exit(0);
        }

        String path = args[0];
        PropertyConfigurator.configure(
                BooksCommonMerger.class.getResourceAsStream("/log4j.properties"));
        Logger.getLogger(BooksCommonMerger.class).info("BISIS5 merging book covers and sinopsis via isbn starting...");

        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
        root.setLevel(ch.qos.logback.classic.Level.INFO);


        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(LibraryPrefixProvider.class);
        ctx.register(ReindexConfigMongo.class);
        ctx.register(ReindexConfigElastic.class);
        ctx.refresh();

        try (Stream<Path> walk = Files.walk(Paths.get(path))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());
            result.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
