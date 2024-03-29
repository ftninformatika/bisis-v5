package com.ftninformatika.bisis.opac.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author badf00d21  5.8.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FilterItem {
    private String label;
    private String value;
    private boolean checked;
    private int count;
}
