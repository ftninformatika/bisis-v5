package com.ftninformatika.bisis.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryStatusPair {
    private String fromStatusCoder;
    private String toStatusCoder;
}
