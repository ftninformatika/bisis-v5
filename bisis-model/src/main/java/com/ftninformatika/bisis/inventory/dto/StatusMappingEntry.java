package com.ftninformatika.bisis.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusMappingEntry {
    private String inventoryStatusCoderId;
    private String itemStatusCoderId;
    private Date itemStatusDate;
    private String note;
}
