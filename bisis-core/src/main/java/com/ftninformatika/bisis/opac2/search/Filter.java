package com.ftninformatika.bisis.opac2.search;

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
public class Filter {
    private FilterItem filter = new FilterItem();
    private List<FilterItem> children = new ArrayList<>();
}
