package com.ftninformatika.bisis.librarian.dto;
import lombok.*;

import java.util.ArrayList;

/**
 * Created by Petar on 8/10/2017.
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LibrarianContextDTO {

    private String pref1;
    private String pref2;
    private String pref3;
    private String pref4;
    private String pref5;
    private ProcessTypeDTO defaultProcessType;
    private ArrayList<ProcessTypeDTO> processTypes = new ArrayList<>();
}
