package com.ftninformatika.bisis.coders.definition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value={ "usage" }, allowSetters = true)
public class CoderDefinition {
    String name;
    String displayName;
    List<Column> columns;
    //@JsonIgnore
    List<Usage> usage;
}

