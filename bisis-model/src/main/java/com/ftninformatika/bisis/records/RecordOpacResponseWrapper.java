package com.ftninformatika.bisis.records;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecordOpacResponseWrapper {

    private Record fullRecord;
    private RecordPreview recordPreview;
    private List<PrimerakPreview> listOfItems;
}
