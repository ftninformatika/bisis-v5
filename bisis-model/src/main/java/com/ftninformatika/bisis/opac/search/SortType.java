package com.ftninformatika.bisis.opac.search;

/**
 * @author badf00d21  15.8.19.
 */
public enum SortType {
    SORT_AUTHOR("AU_sort"),
    SORT_TITLE("TI_sort"),
    SORT_YEAR("PY_sort"),
    SORT_PUBLISHER("PU_sort");

    private String val;

    SortType(String val) {this.val = val;}

    public String toString() {
        return this.val;
    }

}
