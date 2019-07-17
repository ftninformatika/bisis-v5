package com.ftninformatika.bisis.prefixes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Model with purpose to store unchanged data into elastic
 * in order to satisfy "autocomplete OF_SOMETHING" search
 * @author badf00d21  17.7.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "#{@libraryPrefixProvider.getLibPrefix()}library_domain", type = "book_data")
public class ElasticBookShortRaw {
    private String _id;
    private List<String> titles;
    private List<String> authors = new ArrayList<>();
    private List<String> keywords;
    private List<String> publishers;
}
