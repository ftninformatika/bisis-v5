package com.ftninformatika.bisis.books_common_merger;

import com.ftninformatika.bisis.opac2.books.BookCommon;
import com.ftninformatika.utils.string.LatCyrUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

class BooksCommonMergerUtils {

    private static String[] PHOTO_EXTENSIONS = {".jpg", ".png", ".gif"};
    static int UID_COUNTER = 1;
    private static String PLACEHOLDER_COVER_URL = "https://www.makart.rs/__public/dev/img/makart2.png";

    static MultipartFile getCoverMultipart(String path, List<String> lof) throws IOException {
        String bookCoverPath = getCoverPhotoPart(path, lof);
        if (bookCoverPath == null) return null;
        String[] pathChunks = bookCoverPath.split("/");
        String name = pathChunks[pathChunks.length - 1];
        File file = new File(bookCoverPath);
        FileInputStream input = new FileInputStream(file);
        MultipartFile mpFile = new MockMultipartFile("file", name, "image", IOUtils.toByteArray(input));
        input.close();
        return mpFile;
    }

    static boolean bookCoverValid(String path) {
        JSONObject jo = getJSONObjFromPath(path);
        if (jo == null) return false;
        try {
            return (!
                    (jo.getString("korice_url") == null
                    || jo.getString("korice_url").equals(PLACEHOLDER_COVER_URL))
            );
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static BookCommon getBookCommonFromPath(String path) {
        JSONObject jo = getJSONObjFromPath(path);
        if (jo == null) return null;
        try {
            BookCommon bookCommon = new BookCommon();
            bookCommon.setIsbn(jo.getString("isbn").trim());
            bookCommon.setUid(UID_COUNTER);
            bookCommon.setDescription(LatCyrUtils.toCyrillic(jo.getString("opis")));
            bookCommon.setTitle(LatCyrUtils.toCyrillic(jo.getString("naslov")));
            return bookCommon;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getCoverPhotoPart(String path, List<String> lof) {
        if (path == null) return null;
        String withoutExtension = path.substring(0, path.length() - 5);
        for(String s: PHOTO_EXTENSIONS) {
            if (lof.contains(withoutExtension + s)) return (withoutExtension + s);
        }
        return null;
    }

    private static JSONObject getJSONObjFromPath(String path) {
        if (path == null) return null;
        try (InputStream is = Files.newInputStream(Paths.get(path))) {
            String jsonTxt = IOUtils.toString(is);
            JSONObject jsonObject = new JSONObject(jsonTxt);
            is.close();
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
