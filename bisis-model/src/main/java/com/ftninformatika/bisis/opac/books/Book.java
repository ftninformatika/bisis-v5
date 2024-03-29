package com.ftninformatika.bisis.opac.books;

import com.ftninformatika.bisis.records.AvgRecordRating;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Subfield;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author badf00d21  29.7.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    public static String UNIMARC_IMAGE_URL_SUBFIELD = "856x";
    //    Record mongoId
    private String _id;
    private Integer pubType;
    private List<String> authors = new ArrayList<>();
    private List<String> otherAuthors = new ArrayList<>();
    private String title;
    private String subtitle;
    private String publisher;
    private String publishYear;
    private String publishPlace;
    private String isbn;
    private String issn;
    private String pagesCount;
    private String dimensions;
    private Record record = null;
    private String udk;
    private String notes;
    private String isbdHtml;
    private List<Item> items = null;
    private int totalRatings;
    private AvgRecordRating avgRating;
    private String masterRecordTitle;
    private String masterRecordId;
    private Map<String, String> refRecsBrief = new HashMap<>();
    //    Data from books_common collection
    private String imageUrl;
    private String description;
    private Integer commonBookUID;
    private String digitalUrl;       // subfield 856u

    public boolean setUnimarcImageURL(Record record) {
        Subfield urlSubfield = record.getSubfield(Book.UNIMARC_IMAGE_URL_SUBFIELD);
        if (urlSubfield == null || urlSubfield.getContent() == null || urlSubfield.getContent().trim().equals("")) {
            return false;
        } else {
            this.imageUrl = urlSubfield.getContent();
            return true;
        }
    }

    public void setDigitalUrl(Record record) {
        if (record != null) {
            Subfield digitalUrl = record.getSubfield("856u");
            if (digitalUrl == null || digitalUrl.getContent() == null || digitalUrl.getContent().trim().equals("")) {
                this.digitalUrl = null;
            } else {
                this.digitalUrl = digitalUrl.getContent();
            }
        }
    }
}
