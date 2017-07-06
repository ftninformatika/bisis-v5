package com.ftninformatika.bisis.prefixes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;


/**
 * Created by Petar on 7/5/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "library_domain", type = "record")
public class ElasticPrefixEntity {

    @Id String id;
    private List<PrefixValue> prefixes;
}
