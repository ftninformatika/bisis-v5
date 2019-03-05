package com.ftninformatika.bisis.dup_purger;

import com.ftninformatika.bisis.records.Record;

import java.util.List;
import java.util.stream.Collectors;

public class DupInvs {

    public static Record getIvSubSetRec(Record rec1, Record rec2) {
        Record retVal = null;

        List<String> primerci1 = rec1.getPrimerci().stream().map(p -> p.getInvBroj()).collect(Collectors.toList());
        List<String> primerci2 = rec2.getPrimerci().stream().map(p -> p.getInvBroj()).collect(Collectors.toList());

        if (primerci1.size() > 0 && primerci2.size() > 0) {
            if (primerci1.containsAll(primerci2))
                retVal = rec2;
            else if (primerci2.containsAll(primerci1))
                retVal = rec1;
        }

        return retVal;
    }


}
