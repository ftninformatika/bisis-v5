package com.ftninformatika.bisis.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_itemAvailability")
public class ItemAvailability implements java.io.Serializable{

    @Id
    private String _id;
    private Boolean borrowed;
    private String ctlgNo;
    private String recordID;
    private Integer rn;
    private String libDepartment;
    private Boolean reserved;

    @Override
    public String toString() {
        return "{" +
                "ctlgNo='" + ctlgNo + '\'' +
                '}';
    }
}
