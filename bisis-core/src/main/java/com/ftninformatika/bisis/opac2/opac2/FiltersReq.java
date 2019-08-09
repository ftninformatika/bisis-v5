package com.ftninformatika.bisis.opac2.opac2;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author badf00d21  9.8.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltersReq {
    private List<String> locations = new ArrayList<>();
    private List<String> subLocations = new ArrayList<>();
    private List<String> authors = new ArrayList<>();
    private List<String> pubTypes = new ArrayList<>();
    private List<String> languages = new ArrayList<>();
    private List<String> pubYears = new ArrayList<>();
}
