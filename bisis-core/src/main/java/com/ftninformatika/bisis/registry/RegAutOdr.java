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
@Document(collection = "registries.author")
public class RegAutOdr extends Registry {
    @Id private String _id;
    private String autor;
    private String original;
}
