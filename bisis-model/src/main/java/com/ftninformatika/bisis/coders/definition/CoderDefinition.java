package com.ftninformatika.bisis.coders.definition;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoderDefinition {
    String name;
    String displayName;
    List<Column> columns;
    @JsonIgnore
    List<Usage> usage;
}

