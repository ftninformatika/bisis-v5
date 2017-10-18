package com.ftninformatika.bisis.records;

import com.ftninformatika.bisis.prefixes.ElasticPrefixEntity;
import lombok.*;

import java.util.List;

/**
 * Created by Petar on 10/12/2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecordResponseWrapper {

    private ElasticPrefixEntity prefixEntity;
    private Record fullRecord;
    private List<ItemAvailability> listOfItems;

}
