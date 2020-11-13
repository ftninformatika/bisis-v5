package com.ftninformatika.bisisris.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class LibrarianReport {
    String serviceType;
    int inLibrary;
    int byPhone;
    int online;

}
