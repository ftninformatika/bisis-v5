package com.ftninformatika.bisis.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapStatusesToItemsDTO {
    private String inventoryId;
    private List<StatusMappingEntry> statusMapEntryList;
}
