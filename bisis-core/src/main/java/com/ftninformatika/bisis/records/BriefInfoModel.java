package com.ftninformatika.bisis.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author badf00d21  26.3.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BriefInfoModel {
    private Boolean selected = false;
    private String libPrefix;
    private String libFullName;
    private RecordBriefs briefInfo;
}
