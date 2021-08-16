package com.ftninformatika.bisis.opac.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddToCollectionDTO {
    private String collectionId;
    private String recordId;
}
