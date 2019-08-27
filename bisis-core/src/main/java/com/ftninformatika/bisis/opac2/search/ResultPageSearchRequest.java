package com.ftninformatika.bisis.opac2.search;

import com.ftninformatika.bisis.search.SearchModel;
import io.swagger.annotations.ApiModelProperty;
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
public class ResultPageSearchRequest {
    private SearchModel searchModel;
    @ApiModelProperty(hidden = true)
    private ResultPageOptions options;
}
