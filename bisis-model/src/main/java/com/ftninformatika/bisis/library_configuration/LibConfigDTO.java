package com.ftninformatika.bisis.library_configuration;

import com.ftninformatika.bisis.coders.Sublocation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LibConfigDTO {
    private String libraryName;
    private String libraryFullName;
    private String shortName;
    private List<Sublocation> sublocations = new ArrayList<Sublocation>();

    public LibConfigDTO(String libraryName, String libraryFullName, String shortName){
        this.libraryName = libraryName;
        this.libraryFullName = libraryFullName;
        this.shortName = shortName;
    }

}
