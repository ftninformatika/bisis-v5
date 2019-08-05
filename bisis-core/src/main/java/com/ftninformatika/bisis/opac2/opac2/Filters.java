package com.ftninformatika.bisis.opac2.opac2;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author badf00d21  5.8.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filters {
    private List<LocationFilterWrapper> locations = new ArrayList<>();
    private List<String> authors = new ArrayList<>();
    private List<String> pubTypes = new ArrayList<>();
    private List<String> languages = new ArrayList<>();
    private List<String> pubYears = new ArrayList<>();
}
