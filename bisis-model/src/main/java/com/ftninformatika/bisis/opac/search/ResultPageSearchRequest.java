package com.ftninformatika.bisis.opac.search;

import com.ftninformatika.bisis.search.SearchModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author badf00d21  5.8.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultPageSearchRequest {
    private SearchModel searchModel;
    private List<String> recordsIds;
    private ResultPageOptions options;
}
