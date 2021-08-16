package com.ftninformatika.bisis.records;

import lombok.*;

/**
 * @author badf00d21  26.3.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BriefInfoModel {
    private Boolean selected = false;
    private String libPrefix;
    private String libFullName;
    private RecordBriefs briefInfo;
}
