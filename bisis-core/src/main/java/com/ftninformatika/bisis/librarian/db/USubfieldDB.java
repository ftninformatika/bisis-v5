package com.ftninformatika.bisis.librarian.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class USubfieldDB {
    private String fieldName;
    private char subfieldName;
    private String defaultValue;
}
