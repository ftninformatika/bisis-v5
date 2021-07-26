package com.ftninformatika.bisis.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryBook {
    private String name;
    private String code;
    private Integer lastNo;
}
