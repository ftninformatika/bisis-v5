package com.ftninformatika.bisis.opac2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author badf00d21  19.8.19.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "book_covers")
public class BookCover {
    @Id
    private String _id;

    private Integer bookCommonsId;
    private Binary imageBin;
    private String link;
}


