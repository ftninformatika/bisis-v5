package com.ftninformatika.bisis.records.sort;


import java.util.*;
import java.util.stream.Collectors;

/**
 * @author badf00d21  4.6.19.
 */
public class SearchResultsSorter {

    public static String[] POSSIBLE_SORT_OPTIONS = {"AU", "PY", "RN", "TI"};


    public static List<String> sortIds (List<SortingRecElement> sortElements, String sortOption) {
        List<String> retVal = new ArrayList<>();
        if (sortElements.size() == 0 || sortOption == null) return retVal;

        if (!Arrays.asList(POSSIBLE_SORT_OPTIONS).contains(sortOption)) return sortElements.stream().map(SortingRecElement::get_id).collect(Collectors.toList());

        if (sortOption.equals("PY") || sortOption.equals("RN")) {
            sortElements.sort(Comparator.comparing(SortingRecElement::getSortPrefNumVal));
        } else {
            sortElements.sort(Comparator.comparing(SortingRecElement::getSortPrefStringVal));
        }
        return sortElements.stream().map(el -> el.get_id()).collect(Collectors.toList());
    }
}
