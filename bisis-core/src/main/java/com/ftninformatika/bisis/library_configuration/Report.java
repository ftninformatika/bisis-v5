package com.ftninformatika.bisis.library_configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by dboberic on 11/10/2017.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    private String library;
    private String reportName;
    private String className;
    private String invnumpattern;
    private String menuitem;
    private String type;
    private String part;
    private String jasper;
    private String subjasper;
    private String reportTitle;

}
