package com.ftninformatika.bisis.opac2.search;

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
public class ResultPageOptions {
    private Integer pageSize = 10;
    private Integer currentPage = 0;
    private FiltersReq filters;
    private String sort;
    private String previewType;
    private String lib;
}
