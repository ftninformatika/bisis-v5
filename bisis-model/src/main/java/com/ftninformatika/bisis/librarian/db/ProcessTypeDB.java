package com.ftninformatika.bisis.librarian.db;

import com.ftninformatika.bisis.librarian.ProcessType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "coders.process_types")
public class ProcessTypeDB {
    @Id
    private String _id;
    private String name;
    private int pubType;
    private String libName;
    private List<USubfieldDB> initialFields;
    private List<USubfieldDB> mandatoryFields;


    public ProcessTypeDB(ProcessType processType) {
        this._id = processType.get_id();
        this.name = processType.getName();
        this.libName = processType.getLibName();
        this.pubType = processType.getPubType().getPubType();

        if (processType.getInitialSubfields() != null && processType.getInitialSubfields().size() > 0) {
            this.initialFields = processType.getInitialSubfields().stream()
                    .map(i -> new USubfieldDB(i.getOwner().getName(), i.getName(), i.getDefaultValue()))
                    .collect(Collectors.toList());
        }
        if (processType.getMandatorySubfields() != null && processType.getMandatorySubfields().size() > 0) {
            this.mandatoryFields = processType.getMandatorySubfields().stream()
                    .map(i -> new USubfieldDB(i.getOwner().getName(), i.getName(), i.getDefaultValue()))
                    .collect(Collectors.toList());
        }
    }
}
