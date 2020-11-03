package com.ftninformatika.utils;

import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;

public class LocationHelper {

    public static String getLocationCodeByPrimerak(Record record, String ctlgNo, String library){
        String coder_id = "";

        for (Primerak p : record.getPrimerci()){
            if (p.getInvBroj().equals(ctlgNo)){
                if (library.equals("bgb") || library.equals("bmb") || library.equals("bvao")) {
                    coder_id = p.getSigPodlokacija();
                } else {
                    if (p.getOdeljenje() == null || p.getOdeljenje().equals("")) {
                        coder_id = p.getInvBroj().substring(0,2);
                    }else {
                        coder_id = p.getOdeljenje();
                    }
                }
            }
        }
        return coder_id;
    }
}
