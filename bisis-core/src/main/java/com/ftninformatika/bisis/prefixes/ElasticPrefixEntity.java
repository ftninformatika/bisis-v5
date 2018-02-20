package com.ftninformatika.bisis.prefixes;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;
import java.util.Map;


/**
 * Created by Petar on 7/5/2017.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(indexName = "#{libraryPrefixProvider.getLibPrefix()}_library_domain", type = "record")
public class ElasticPrefixEntity {

    @Id String id;
    private Map<String,List<String>> prefixes;

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append(id + "\n");
        for(Map.Entry<String,List<String>> entry: prefixes.entrySet()){
            sb.append(entry.getKey() + ": \n");
                    for(String s: entry.getValue())
                        sb.append(s + "\n");
            sb.append("---------------\n");
        }
        return sb.toString();
    }

}
