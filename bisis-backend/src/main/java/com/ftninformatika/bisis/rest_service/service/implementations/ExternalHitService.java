package com.ftninformatika.bisis.rest_service.service.implementations;

import com.ftninformatika.bisis.opac2.books.Book;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author badf00d21  6.11.19.
 */
@Service
public class ExternalHitService {

    @Autowired RecordsRepository recordsRepository;
    @Autowired OpacSearchService opacSearchService;


    public String getBookHtml(String url) {
        if (url == null) return null;
        List<String> urlChunks = Arrays.asList(url.split("/"));
        if (urlChunks.size() == 0 || !urlChunks.contains("book")) return null;
        int lastIndex = urlChunks.size() - 1;
        try {
            String record_id = urlChunks.get(lastIndex);
            String lib = urlChunks.get(lastIndex - 1);
            LibraryPrefixProvider.setPrefix(lib);
            Optional<Record> record = recordsRepository.findById(record_id);
            if (!record.isPresent()) return null;
            Book book = opacSearchService.getBookByRec(record.get());
            return generateHtml(book, url);
        }
        catch (Exception e) {
         e.printStackTrace();
         return null;
        }
    }

//    TODO - move this to some place nicer (template or something)
    private String generateHtml(Book book, String url) {
        if (book == null) return null;
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("<html lang=\"sr\">");
            sb.append("<head>");
            sb.append("<title>" + book.getTitle() + "</title>");
            sb.append("<meta charser=\"utf-8\">");
            sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
            sb.append("<meta property=\"og:url\" content=\"" + url + "\">");
            sb.append("<meta property=\"og:title\" content=\"" + book.getTitle() + "\">");
            sb.append("<meta property=\"og:description\" content=\"" + book.getDescription() + "\">");
            sb.append("<meta property=\"og:site_name\" content=\"OPAC2\">");
            sb.append("<meta property=\"og:type\" content=\"book\">");
            sb.append("<meta property=\"og:image\" content=\"" + book.getImageUrl() + "\">");
            sb.append("</head>");
            sb.append("</html>");
            return sb.toString();
        }
        catch (Exception e) {
            return null;
        }
    }

}
