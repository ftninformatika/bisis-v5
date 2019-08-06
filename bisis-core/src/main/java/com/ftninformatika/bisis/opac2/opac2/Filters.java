package com.ftninformatika.bisis.opac2.opac2;

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

    public FilterItem getSublocationByValue(String val) {
        if (val == null || val.length() < 2)
            return null;
        Filter f = getLocationByValue(val.substring(0,2));
        if (f == null || f.getChildren().size() == 0) return null;
        for (FilterItem fi: f.getChildren()) {
            if (fi.getValue().equals(val)) return fi;
        }
        return null;
    }

    public void sortFilters() {
        locations.sort(Comparator.comparing(f -> f.getFilter().getValue()));
        authors.sort(Comparator.comparing(f -> f.getFilter().getCount()));
        Collections.reverse(authors);
        pubTypes.sort(Comparator.comparing(f -> f.getFilter().getCount()));
        Collections.reverse(pubTypes);
        languages.sort(Comparator.comparing(f -> f.getFilter().getCount()));
        Collections.reverse(languages);
        pubYears.sort(Comparator.comparing((f -> f.getFilter().getValue())));
        Collections.reverse(pubYears);
    }
}
