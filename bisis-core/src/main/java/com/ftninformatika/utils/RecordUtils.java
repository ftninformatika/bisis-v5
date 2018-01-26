package com.ftninformatika.utils;

import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Petar on 1/26/2018.
 */
public class RecordUtils {

    public static List<ItemAvailability> getItemAvailabilityNewDelta(Record freshRecord, Record storedRecord){
        List<ItemAvailability> retVal = new ArrayList<>();
        if (freshRecord.getPrimerci() != null && storedRecord.getPrimerci() != null && freshRecord.getPrimerci().size() > 0 && storedRecord.getPrimerci().size() > 0){
            for (Primerak p: freshRecord.getPrimerci())
                if (!storedRecord.getPrimerci().stream().map(e -> e.getInvBroj()).collect(Collectors.toList()).contains(p.getInvBroj())){
                    ItemAvailability ia = new ItemAvailability();
                    ia.setCtlgNo(p.getInvBroj());
                    ia.setBorrowed(false); //ako je tek inventarisan ne moze biti izdat???
                    ia.setRecordID(String.valueOf(freshRecord.getRecordID()));
                    ia.setLibDepartment(p.getOdeljenje());
                    retVal.add(ia);
                }
        }

        if (freshRecord.getGodine() != null && storedRecord.getGodine() != null && freshRecord.getGodine().size() > 0 && storedRecord.getGodine().size() > 0){
            for (Godina p: freshRecord.getGodine())
                if (!storedRecord.getGodine().stream().map(e -> e.getInvBroj()).collect(Collectors.toList()).contains(p.getInvBroj())){
                    ItemAvailability ia = new ItemAvailability();
                    ia.setCtlgNo(p.getInvBroj());
                    ia.setBorrowed(false);
                    ia.setRecordID(String.valueOf(freshRecord.getRecordID()));
                    ia.setLibDepartment(p.getOdeljenje());
                    retVal.add(ia);
                }
        }
        return retVal;
    }

    public static List<String> getDeletedInvNumsDelta(Record freshRecord, Record storedRecord){
        List<String> retVal = new ArrayList<>();

        if (storedRecord.getPrimerci().size() > 0){
            for (Primerak p: storedRecord.getPrimerci())
                if (!freshRecord.getPrimerci().stream().map(r -> r.getInvBroj()).collect(Collectors.toList()).contains(p.getInvBroj()))
                    retVal.add(p.getInvBroj());
        }


        if (storedRecord.getGodine().size() > 0){
            for (Godina g: storedRecord.getGodine())
                if (!freshRecord.getGodine().stream().map(r -> r.getInvBroj()).collect(Collectors.toList()).contains(g.getInvBroj()))
                    retVal.add(g.getInvBroj());
        }

        return retVal;
    }
}
