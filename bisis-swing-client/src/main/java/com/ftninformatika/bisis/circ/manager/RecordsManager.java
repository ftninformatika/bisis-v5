package com.ftninformatika.bisis.circ.manager;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.coders.CodersHelper;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Sveska;
import com.ftninformatika.bisis.service.BisisService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecordsManager {

    private Primerak primerak;
    private Sveska sveska;
    private List<Object> list;

    public RecordsManager(){

    }

    private void init(){
        list = new ArrayList<>();

    }

    public Record lendBook(String ctlgno){
        Record retVal = null;
        boolean zaduziv = false;
        primerak = null;

        try {
            primerak = BisisApp.bisisService.getPrimerakByInvNum(ctlgno).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(primerak != null){
            //listContainsPrimerak???
            if(primerak.getStatus() == null){
                zaduziv = true;
            }
            else{
                //sifarnici u bazi moraju biti validni
                zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get( primerak.getStatus()).isLendable();
            }

            if(zaduziv == true){
                if(primerak.getStanje() == 0){
                    primerak.setStanje(1);
                    list.add(primerak);
                    try {
                        retVal = BisisApp.bisisService.getRecordForPrimerak(ctlgno).execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        else {
            //TODO- implementirati za svesku isto
            /*
            if (sveska != null){
                listContainsSveska(sveska);
                if (sveska.getStatusPrimerka() == null){
                    zaduziv = 1;
                }else{
                    zaduziv = sveska.getStatusPrimerka().getZaduziv();
                }
                if (zaduziv == 1){
                    if (sveska.getStanje() != 1){
                        sveska.setStanje(1);
                        list.add(sveska);
                        record = getRecord(ctlgno);
                        //record = BisisApp.getRecordManager().getRecord(sveska.getGodine().getRecords().getRecordId());
                    }
                }
            }*/
        }
        return retVal;
    }
}
