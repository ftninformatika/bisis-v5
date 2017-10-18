package com.ftninformatika.bisis.models.circ;

import lombok.*;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by Petar on 10/16/2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Document(collection = "library_members")
public class LibraryMember {

    @Id String _id;
    private String username; // (email)
    private String password;
    private String libraryPrefix;
    private String index;
    private String passwordResetString;


}
