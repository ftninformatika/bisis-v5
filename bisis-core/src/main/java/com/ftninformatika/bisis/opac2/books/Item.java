package com.ftninformatika.bisis.opac2.books;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author badf00d21  20.8.19.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String location;
    private String locCode;
    private String signature;
    private String status;
    private String invNum;
//    Periodicne sadrze ovo ispod
    private String volume;
    private String year;
    private String number;
    private boolean serial;
}
