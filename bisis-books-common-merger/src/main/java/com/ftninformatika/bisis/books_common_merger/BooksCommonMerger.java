package com.ftninformatika.bisis.books_common_merger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.controller.opac2.BookCommonController;
import com.ftninformatika.bisis.rest_service.controller.opac2.BookCoverController;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author badf00d21  25.9.19.
 */
@ComponentScan("com.ftninformatika")
public class BooksCommonMerger {

    private static Logger log = Logger.getLogger(BooksCommonMerger.class);
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static String[] photoExtensions = {".jpg", ".png", ".gif"};
    private static int UID_COUNTER = 1;

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
        ctx.register(CommonMergerConfigMongo.class);
        ctx.register(CommonMergerConfigElastic.class);
        ctx.refresh();
        ctx.scan("com.ftninformatika");
        BookCommonController bookCommonController = ctx.getBean(BookCommonController.class);
//        ctx.scan("com.ftninformatika.bisis.rest_service");
        BookCoverController bookCoverController = ctx.getBean(BookCoverController.class);

        try (Stream<Path> walk = Files.walk(Paths.get(path))) {

            List<String> files = walk.filter(Files::isRegularFile)
                    .map(Objects::toString).collect(Collectors.toList());

            for (String fileName: files) {
                if (!fileName.endsWith(".json")) continue;
                BookCommon bookCommon = getBookCommonFromPath(fileName);
                if (bookCommon == null) {
                    log.warn("Cannot make BookCommon from path: " + fileName);
                    continue;
                }
//                TODO: put merging to other library records logic here
//                validate isbn-s
                BookCommon returnedBookCommon = bookCommonController.saveModifyBookCommon(bookCommon).getBody();
                if (returnedBookCommon == null) {
                    log.warn("Server error for saving object from file: " + path);
                    continue;
                }
                UID_COUNTER++;
                MultipartFile coverMultipart = getCoverMultipart(fileName, files);
                if (!bookCoverController.uploadImage(bookCommon.getUid(), coverMultipart).getBody()) {
                    log.info("No cover image for file: " + path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCoverPhotoPart(String path, List<String> lof) {
        if (path == null) return null;
        String withoutExtension = path.substring(0, path.length() - 5);
        for(String s: photoExtensions) {
            if (lof.contains(withoutExtension + s)) return (withoutExtension + s);
        }
        return null;
    }

    private static MultipartFile getCoverMultipart(String path, List<String> lof) throws IOException {
        String bookCoverPath = getCoverPhotoPart(path, lof);
        if (bookCoverPath == null) return null;
        String[] pathChunks = bookCoverPath.split("/");
        String name = pathChunks[pathChunks.length - 1];
        File file = new File(bookCoverPath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file", name, "image", IOUtils.toByteArray(input));
        return multipartFile;
    }

    private static BookCommon getBookCommonFromPath(String path) {
        JSONObject jo = getJSONObjFromPath(path);
        if (jo == null) return null;
        try {
            BookCommon bookCommon = new BookCommon();
            bookCommon.setIsbn(jo.getString("isbn"));
            bookCommon.setUid(UID_COUNTER);
            bookCommon.setDescription(jo.getString("opis"));
            bookCommon.setTitle(jo.getString("naslov"));
            return bookCommon;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JSONObject getJSONObjFromPath(String path) {
        if (path == null) return null;
        InputStream is = null;
        try {
            is = Files.newInputStream(Paths.get(path));
            String jsonTxt = IOUtils.toString(is);
            JSONObject jsonObject = new JSONObject(jsonTxt);
            is.close();
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
