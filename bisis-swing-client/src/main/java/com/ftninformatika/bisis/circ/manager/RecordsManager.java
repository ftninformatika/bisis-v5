package com.ftninformatika.bisis.circ.manager;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.ItemAvailability;
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
    private ItemAvailability itemAvailability;
    private List<ItemAvailability> list;


    public RecordsManager(){
        list = new ArrayList<ItemAvailability>();
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
            if (primerak.getStatus() == null){
                zaduziv = true;
            } else {
                zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get(primerak.getStatus()).isLendable();
            }
        } else {
            try {
                sveska = BisisApp.bisisService.getSveskaByInvNum(ctlgno).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sveska != null){
                if (sveska.getStatus() == null){
                    zaduziv = true;
                }else{
                    zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get( sveska.getStatus()).isLendable();
                }
            }
        }
        if (zaduziv){
            try {
                itemAvailability = BisisApp.bisisService.getItemAvailability(ctlgno).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            listContainsItem(itemAvailability);
            if (!itemAvailability.getBorrowed()){
                itemAvailability.setBorrowed(true);
                list.add(itemAvailability);
                record = getRecord(ctlgno);
            }
        }
        return record;
    }

    public void listContainsItem(ItemAvailability item){
        Iterator<ItemAvailability> it = list.iterator();
        while (it.hasNext()){
            ItemAvailability tmp = it.next();
            if (tmp.getCtlgNo() == item.getCtlgNo()){
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
            try {
                itemAvailability = BisisApp.bisisService.getItemAvailability(ctlgno).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            listContainsItem(itemAvailability);
            if (itemAvailability.getBorrowed()){
                itemAvailability.setBorrowed(false);
                list.add(itemAvailability);
            }
        } else {
            try {
                sveska = BisisApp.bisisService.getSveskaByInvNum(ctlgno).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sveska != null){
                try {
                    itemAvailability = BisisApp.bisisService.getItemAvailability(ctlgno).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                listContainsItem(itemAvailability);
                if (itemAvailability.getBorrowed()){
                    itemAvailability.setBorrowed(false);
                    list.add(itemAvailability);
                }
            }
        }
    }

//    public Object changeStanje(String ctlgno){
//        try {
//            primerak = BisisApp.bisisService.getPrimerakByInvNum(ctlgno).execute().body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (primerak != null){
//            if (primerak.getStanje() == 1){
//                primerak.setStanje(0);
//            }
//            return primerak;
//        } else {
//            try {
//                sveska = BisisApp.bisisService.getSveskaByInvNum(ctlgno).execute().body();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            if (sveska != null){
//                if (sveska.getStanje() == 1){
//                    sveska.setStanje(0);
//                }
//            }
//            return sveska;
//        }
//    }

    public String getErrorMessage(){
        String message = "";
        if (primerak == null && sveska == null){
            message = "Nepostojeci inventarni broj!";
        } else if (primerak != null){
            message = "Status primerka: ";
            if (primerak.getStatus() != null){
                message = message + primerak.getStatus() + ", ";
            }
            if (itemAvailability.getBorrowed()){
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
            if (itemAvailability.getBorrowed()){
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
                return itemAvailability.getBorrowed();
            }
            return false;
        }else if (sveska != null){
            if (sveska.getStatus() == null || BisisApp.appConfig.getCodersHelper().getItemStatuses().get(sveska.getStatus()).isLendable()){
                return itemAvailability.getBorrowed();
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
            record = BisisApp.bisisService.getRecordByCtlgNo(ctlgno).execute().body();
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

