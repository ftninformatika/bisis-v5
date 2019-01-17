package com.ftninformatika.bisis.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * Created by dboberic on 15/10/2017.
 */
@Getter
@Setter
@JsonIgnoreProperties( ignoreUnknown = true )
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_reports")
public class GeneratedReport implements Serializable{
    String reportName;
    String fullReportName;
    String reportType;
    String period;
    String content;


}
