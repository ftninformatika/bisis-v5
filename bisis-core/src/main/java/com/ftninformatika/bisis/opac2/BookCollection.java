package com.ftninformatika.bisis.opac2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author badf00d21  19.9.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_book_collections")
public class BookCollection {
    public static int MAX_SIZE = 30;
    @Id()
    private String _id;
    private String creatorUsername;
    private Date creationDate;
    private Date lastModified;
    private List<String> bookIds = new ArrayList<>();

}