package com.ftninformatika.bisis.opac.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author badf00d21  5.8.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filters {
    private List<Filter> locations = new ArrayList<>();
    private List<Filter> authors = new ArrayList<>();
    private List<Filter> pubTypes = new ArrayList<>();
    private List<Filter> languages = new ArrayList<>();
    private List<Filter> pubYears = new ArrayList<>();
    private List<Filter> subjects = new ArrayList<>();

    public Filter getAuthorByValue(String val) {
        if (val == null)
            return null;
        for (Filter f: authors)
            if (f.getFilter().getValue().equals(val))
                return f;
        return null;
    }

    public Filter getPubYearByValue(String val) {
        if (val == null)
            return null;
        for (Filter f: pubYears)
            if (f.getFilter().getValue().equals(val))
                return f;
        return null;
    }

    public Filter getPubTypesByValue(String val) {
        if (val == null)
            return null;
        for (Filter f: pubTypes)
            if (f.getFilter().getValue().equals(val))
                return f;
        return null;
    }

    public Filter getLanguagesByValue(String val) {
        if (val == null)
            return null;
        for (Filter f: languages)
            if (f.getFilter().getValue().equals(val))
                return f;
        return null;
    }

    public Filter getLocationByValue(String val) {
        if (val == null || val.length() != 2)
            return null;
        for (Filter f: locations)
            if (f.getFilter().getValue().equals(val))
                return f;
        return null;
    }

    public Filter getSubjectByValue(String val) {
        if (val == null)
            return null;
        for (Filter f: subjects)
            if (f.getFilter().getValue().equals(val))
                return f;
        return null;
    }

    public FilterItem getSublocationByValue(String val, String lbl) {
        if (val == null || val.length() < 4)
            return null;
        String locChunk = val.substring(0, 2);
        Filter f = getLocationByValue(locChunk);
        if (f == null)
            locations.add(new Filter(new FilterItem(lbl, locChunk, false, 0), new ArrayList<>()));
        f = getLocationByValue(locChunk);
        for (FilterItem fi: f.getChildren()) {
            if (fi.getValue().equals(val)) return fi;
        }
        return null;
    }

    public void sortFilters() {
        locations.sort(Comparator.comparing(f -> f.getFilter().getValue()));
        for (Filter f: locations) {
            f.getChildren().sort(Comparator.comparing(FilterItem::getValue));
        }
        authors.sort(Comparator.comparing(f -> f.getFilter().getCount()));
        Collections.reverse(authors);
        pubTypes.sort(Comparator.comparing(f -> f.getFilter().getCount()));
        Collections.reverse(pubTypes);
        languages.sort(Comparator.comparing(f -> f.getFilter().getCount()));
        Collections.reverse(languages);
        pubYears.sort(Comparator.comparing((f -> f.getFilter().getValue())));
        Collections.reverse(pubYears);
        subjects.sort(Comparator.comparing((f -> f.getFilter().getCount())));
        Collections.reverse(subjects);
    }
}
