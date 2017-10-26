package com.ftninformatika.bisis.prefixes;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Map;


/**
 * Created by Petar on 7/5/2017.
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "#{libraryPrefixProvider.getLibPrefix()}_library_domain", type = "record")
public class ElasticPrefixEntity {

    @Id String id;
    private Map<String,String> prefixes;
}
