package com.ftninformatika.bisis.librarian.process_type_dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by Petar on 8/10/2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProcessTypeDTO {

    private String name;
    private int pubType;
    private String libName;
    private List<USubfieldDTO> initialFields;
    private List<USubfieldDTO> mandatoryFields;
}
