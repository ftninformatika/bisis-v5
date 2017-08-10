package com.ftninformatika.bisis.librarian.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by Petar on 8/10/2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "coders.process_types")
public class ProcessTypeDTO {

    @Id private String _id;
    private String name;
    private int pubType;
    private String libName;
    private List<USubfieldDTO> initialFields;
    private List<USubfieldDTO> mandatoryFields;
}
