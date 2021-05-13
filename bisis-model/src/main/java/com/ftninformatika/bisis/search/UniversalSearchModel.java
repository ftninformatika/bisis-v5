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
    private List<String> branches = new ArrayList<>();
    private String searchText;

}
