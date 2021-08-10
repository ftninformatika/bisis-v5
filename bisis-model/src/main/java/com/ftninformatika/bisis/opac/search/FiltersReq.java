package com.ftninformatika.bisis.opac.search;

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
    public static String MERGE_FILTER_SERBIAN = "srp";
    private List<SelectedFilter> locations = new ArrayList<>();
    private List<SelectedFilter> subLocations = new ArrayList<>();
    private List<SelectedFilter> authors = new ArrayList<>();
    private List<SelectedFilter> pubTypes = new ArrayList<>();
    private List<SelectedFilter> languages = new ArrayList<>();
    private List<SelectedFilter> pubYears = new ArrayList<>();
    private List<SelectedFilter> subjects = new ArrayList<>();

}
