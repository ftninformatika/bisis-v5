package com.ftninformatika.bisis.mobile;

import com.ftninformatika.bisis.opac.books.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author marijakovacevic
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookAvailabilityDTO {
    private String recordId;
    private String locationDescription;
    private String locationCode;
    private int total;
    private int free;
    private int reserved;

    public BookAvailabilityDTO(String recordId, Map.Entry<String, List<Item>> entry, int total, int free, int reserved) {
        this.setRecordId(recordId);
        this.setLocationCode(entry.getKey());
        this.setLocationDescription(entry.getValue().get(0).getLocation());
        this.setTotal(total);
        this.setFree(free);
        this.setReserved(reserved);
    }
}
