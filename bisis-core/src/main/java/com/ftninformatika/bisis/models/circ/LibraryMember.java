package com.ftninformatika.bisis.models.circ;

import com.ftninformatika.bisis.auth.model.Authority;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

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
    private List<Authority> authorities;

    private String passwordResetString;

}
