package com.ftninformatika.bisis.opac.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author badf00d21  15.8.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sort {
    private SortType type;
    private Boolean ascending;

    public SortType getType() {
        return type;
    }

    public void setType(String st) {
        switch (st) {
            case "AU_sort" : this.type = SortType.SORT_AUTHOR; break;
            case "PY_sort" : this.type = SortType.SORT_YEAR; break;
            case "PU_sort" : this.type = SortType.SORT_PUBLISHER; break;
            case "TI_sort" : this.type = SortType.SORT_TITLE; break;
            default: this.type = null;
        }
    }
}
