package com.ftninformatika.bisis.librarian.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "librarian_roles")
public class LibrarianRoleDB {
    @Id
    private String _id;
    @Indexed(unique = true)
    private String name;
    private String description;
}
