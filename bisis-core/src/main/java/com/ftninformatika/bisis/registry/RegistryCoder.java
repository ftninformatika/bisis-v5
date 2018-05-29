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
@Document(collection = "#{libraryPrefixProvider.getLibPrefix()}_registry_types")
public class RegistryCoder {
    @Id private String _id;
    private String description;
    private Integer code;
}
