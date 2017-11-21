package com.ftninformatika.bisis.search;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Petar on 11/3/2017.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UniversalSearchModel {
    private List<String> departments = new ArrayList<>();
    private String searchText;

    public void fixDepartments(){
        for (int i = 0; i < this.departments.size(); i++){
            if(departments.get(i).length() == 1)
                departments.set(i, "0" + departments.get(i));
        }
    }

}
