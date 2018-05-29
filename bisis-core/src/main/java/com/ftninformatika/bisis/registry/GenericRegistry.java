package com.ftninformatika.bisis.registry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "#{libraryPrefixProvider.getLibPrefix()}_registries")
public class GenericRegistry {
    @Id private String _id;
    //Registry coder
    private Integer code;
    //N fields
    private String field1;
    private String field2;
    private String field3;
}
