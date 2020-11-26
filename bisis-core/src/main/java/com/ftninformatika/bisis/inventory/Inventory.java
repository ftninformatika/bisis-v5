package com.ftninformatika.bisis.inventory;

import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "#{@libraryPrefixProvider.getLibPrefix()}_inventory")
public class Inventory {
    @Id
    private String _id;
    private String name;
    private Integer year;
    private String library;
//    @JsonFormat(pattern="dd.MM.yyyy.")
    private Date startDate;
//    @JsonFormat(pattern="dd.MM.yyyy.")
    private Date endDate;
    private List<Sublocation> sublocations;
    private List<Location> locations;
    private List<InventoryBook> invBooks;
    private List<InventoryStatusPair> invToRevisionStatuses;
    private EnumInventoryState inventoryState;

    public List<InventoryUnit> initListOfUnitsFromRecord(Record record) {
        List<InventoryUnit> retVal = new ArrayList<>();
        RecordPreview rp = new RecordPreview();
        rp.init(record);
        // todo ovde location/sublocation kada bude trebalo
        List<Primerak> filteredPrimerci = record.getPrimerciBySublocations(this.sublocations);
        filteredPrimerci = filteredPrimerci.stream()
                .filter(fp -> fp.inRangeForAnyInvBook(this.invBooks)).collect(Collectors.toList());
        for (Primerak p: filteredPrimerci) {
            InventoryUnit inventoryUnit = new InventoryUnit();
            inventoryUnit.setRn(record.getRN());
            inventoryUnit.setInventoryId(this._id);
            inventoryUnit.setInvNo(p.getInvBroj());
            inventoryUnit.setAuthor(rp.getAuthor());
            inventoryUnit.setTitle(rp.getTitle());
            inventoryUnit.setSignature(p.getSigUDK()); // todo proveriti da li UDK da se koristi
            inventoryUnit.setPublisher(rp.getPublisher());
            inventoryUnit.setPubYear(rp.getPublishingYear());
            inventoryUnit.setInvStatus(p.getStatus());
            inventoryUnit.setRevisionStatus(getRevStatusByInv(p.getStatus()));
            inventoryUnit.setDateModified(new Date());
            retVal.add(inventoryUnit);
        }
        return retVal;
    }

    private String getRevStatusByInv(String invStatus) {
        String retVal = "U_REVIZIJI"; // todo ovde staviti neki default
        if (invToRevisionStatuses == null || invStatus == null) {
            return retVal;
        }
        for (InventoryStatusPair pair: invToRevisionStatuses) {
            if (pair.getFromStatusCoder().equals(invStatus)) {
                retVal = pair.getToStatusCoder();
            }
        }
        return retVal;
    }
}
