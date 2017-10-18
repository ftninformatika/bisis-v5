package com.ftninformatika.bisis.circ.manager;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Sveska;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class RecordsManager {

    private Primerak primerak;
    private Sveska sveska;
    private List<Object> list;


    public RecordsManager(){
        init();
    }

    private void init(){
        list = new ArrayList<Object>();
    }

    public Record lendBook(String ctlgno){
        boolean zaduziv = false;
        Record record = null;

        try {
            primerak = BisisApp.bisisService.getPrimerakByInvNum(ctlgno).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (primerak != null){
            listContainsPrimerak(primerak);
            if (primerak.getStatus() == null){
                zaduziv = true;
            } else {
                zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get(primerak.getStatus()).isLendable();

            }
            if (zaduziv){
                if (primerak.getStanje() == 0){
                    primerak.setStanje(1);
                    list.add(primerak);
                    try {
                        record = BisisApp.bisisService.getRecordForPrimerak(ctlgno).execute().body();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            try {
                sveska = BisisApp.bisisService.getSveskaByInvNum(ctlgno).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sveska != null){
                listContainsSveska(sveska);
                if (sveska.getStatus() == null){
                    zaduziv = true;
                }else{
                    zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get( sveska.getStatus()).isLendable();

                }
                if (zaduziv){
                    if (sveska.getStanje() != 1){
                        sveska.setStanje(1);
                        list.add(sveska);
                        try {
                            record = BisisApp.bisisService.getRecordForSveska(ctlgno).execute().body();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return record;
    }

    public void listContainsPrimerak(Primerak prim){
        Iterator<Object> it = list.iterator();
        while (it.hasNext()){
            Object tmp = it.next();
            if (tmp instanceof Primerak && ((Primerak)tmp).getPrimerakID() == prim.getPrimerakID()){
                list.remove(tmp);
                return;
            }
        }
    }

    public void listContainsSveska(Sveska sv){
        Iterator<Object> it = list.iterator();
        while (it.hasNext()){
            Object tmp = it.next();
            if (tmp instanceof Sveska && ((Sveska)tmp).getSveskaID() == sv.getSveskaID()){
                list.remove(tmp);
                return;
            }
        }
    }

    public void returnBook(String ctlgno){
        try {
            primerak = BisisApp.bisisService.getPrimerakByInvNum(ctlgno).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (primerak != null){
            listContainsPrimerak(primerak);
            if (primerak.getStanje() == 1){
                primerak.setStanje(0);
                list.add(primerak);
            }
        } else {
            try {
                sveska = BisisApp.bisisService.getSveskaByInvNum(ctlgno).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sveska != null){
                listContainsSveska(sveska);
                if (sveska.getStanje() == 1){
                    sveska.setStanje(0);
                    list.add(sveska);
                }
            }
        }
    }

    public Object changeStanje(String ctlgno){
        try {
            primerak = BisisApp.bisisService.getPrimerakByInvNum(ctlgno).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (primerak != null){
            if (primerak.getStanje() == 1){
                primerak.setStanje(0);
            }
            return primerak;
        } else {
            try {
                sveska = BisisApp.bisisService.getSveskaByInvNum(ctlgno).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sveska != null){
                if (sveska.getStanje() == 1){
                    sveska.setStanje(0);
                }
            }
            return sveska;
        }
    }

    public String getErrorMessage(){
        String message = "";
        if (primerak == null && sveska == null){
            message = "Nepostojeci inventarni broj!";
        } else if (primerak != null){
            message = "Status primerka: ";
            if (primerak.getStatus() != null){
                message = message + primerak.getStatus() + ", ";
            }
            if (primerak.getStanje() == 1){
                message = message + "Zauzet";
            } else {
                message = message + "Slobodan";
            }
            primerak = null;
        } else if (sveska != null){
            message = "Status primerka: ";
            if (sveska.getStatus() != null){
                message = message + sveska.getStatus() + ", ";
            }
            if (sveska.getStanje() == 1){
                message = message + "Zauzet";
            } else {
                message = message + "Slobodan";
            }
            sveska = null;
        }
        return message;
    }

    public boolean existBook(){
        if (primerak == null && sveska == null){
            return false;
        }else{
            return true;
        }
    }

    public boolean chargedBook(){
        if (primerak != null ){
            if (primerak.getStatus() == null || BisisApp.appConfig.getCodersHelper().getItemStatuses().get(primerak.getStatus()).isLendable()){
                return primerak.getStanje() == 1;
            }
            return false;
        }else if (sveska != null){
            if (sveska.getStatus() == null || BisisApp.appConfig.getCodersHelper().getItemStatuses().get(sveska.getStatus()).isLendable()){
                return sveska.getStanje() == 1;
            }
            return false;
        }else{
            return false;
        }
    }

    public List getList(){
        return list;
    }

    public void releaseList(){
        list.clear();
    }

    public Record getRecord(String ctlgno){
        Record record = null;
        try {
            record = BisisApp.bisisService.getRecord(ctlgno).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return record;
    }

    public int getRecordId(String ctlgno){
        Record record = getRecord(ctlgno);
        if (record != null) {
            return record.getRecordID();
        } else {
            return 0;
        }
    }

//    public int getRecords(Query q, List list){
//        Result res = null;
//        List resultList = null;
//
//        if (q != null){
//            if (list != null){
//                CachingWrapperFilter filter = new CachingWrapperFilter(new BisisFilter(list));
//                res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
//                resultList = ListUtils.intersection(res.getInvs(),list);
//            }else{
//                res = BisisApp.getRecordManager().selectAll2x(SerializationUtils.serialize(q), "TI_sort");
//                resultList = res.getInvs();
//            }
//        } else {
//            CachingWrapperFilter filter = new CachingWrapperFilter(new BisisFilter(list));
//            res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(new MatchAllDocsQuery()),SerializationUtils.serialize(filter), "TI_sort");
//            resultList = list;
//        }
//        if (res == null){
//            return 0;
//        }else if(res.getRecords().length == 0){
//            return 1;
//        }else{
//            Cirkulacija.getApp().getMainFrame().getSearchBooksResults().setHits(res.getRecords());
//            if (resultList != null)
//                Cirkulacija.getApp().getMainFrame().getSearchBooksResults().setCtlgnoNum(resultList.size());
//            Cirkulacija.getApp().getMainFrame().showPanel("searchBooksResultsPanel");
//            return 2;
//        }
//    }



}

