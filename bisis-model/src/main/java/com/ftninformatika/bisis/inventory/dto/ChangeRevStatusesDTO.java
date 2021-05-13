package com.ftninformatika.bisis.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangeRevStatusesDTO {
    private String inventoryId;
    private String fromRevCoderId;
    private String toRevCoderId;
}
