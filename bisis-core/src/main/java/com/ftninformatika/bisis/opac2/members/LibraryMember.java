package com.ftninformatika.bisis.opac2.members;

import com.ftninformatika.bisis.auth.model.Authority;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
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
    private String index; // _id from xxx_members collection
    private String activationToken; // Contains activation deadline date and represent url for activation
    private Boolean profileActivated;
    private List<Authority> authorities;
    private String authToken; // Access token
    private Date lastActivity; // Used when access token is provided to check if token is still valid
    private String passwordResetString; // Represent url path for password reset

}
