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
}
