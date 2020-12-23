package com.ftninformatika.bisis.circ.manager;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.records.ItemAvailability;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Sveska;
import com.ftninformatika.bisis.search.SearchModelCirc;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class RecordsManager {

    private Primerak primerak;
    private Sveska sveska;
    private ItemAvailability itemAvailability;
    private List<ItemAvailability> listOfItems;
    private List<ItemAvailability> booksToBeReserved;
    private static Logger log = Logger.getLogger(RecordsManager.class);


    public RecordsManager() {
        listOfItems = new ArrayList<>();
        booksToBeReserved = new ArrayList<>();
    }

    public Record reserveBook(String ctlgNo) {
        log.info("Rezervisanje primerka iz BISIS-a sa inventarnim brojem: " + ctlgNo);
        boolean zaduziv = false;

        Primerak primerak = null;
        Record record = null;

        try {
            primerak = BisisApp.bisisService.getPrimerakByInvNum(ctlgNo).execute().body();
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }

        if (primerak != null) {
            if (primerak.getStatus() == null) {
                zaduziv = true;
            } else {
                zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get(primerak.getStatus()).isLendable();
            }
            try {
                itemAvailability = BisisApp.bisisService.getItemAvailability(ctlgNo).execute().body();
            } catch (IOException e) {
                log.error(e);
                e.printStackTrace();
            }
        }

        // check if book is lendable and currently borrowed
        if (zaduziv) {
            if (itemAvailability != null && itemAvailability.getBorrowed()) {
                log.info("Primerak je stavljen u privremenu listu: " + ctlgNo);
                this.booksToBeReserved.add(itemAvailability);
                record = getRecord(ctlgNo);
            }
        }
        return record;
    }

    public Record lendBook(String ctlgno) {
        log.info("Zaduzivanje primerka: " + ctlgno);
        boolean zaduziv = false;
        Record record = null;

        try {
            primerak = BisisApp.bisisService.getPrimerakByInvNum(ctlgno).execute().body();
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }

        if (primerak != null) {
            if (primerak.getStatus() == null) {
                zaduziv = true;
            } else {
                zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get(primerak.getStatus()).isLendable();
            }
            try {
                itemAvailability = BisisApp.bisisService.getItemAvailability(ctlgno).execute().body();
            } catch (IOException e) {
                log.error(e);
                e.printStackTrace();
            }
        } else {
            try {
                sveska = BisisApp.bisisService.getSveskaByInvNum(ctlgno).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sveska != null) {
                if (sveska.getStatus() == null) {
                    zaduziv = true;
                } else {
                    zaduziv = BisisApp.appConfig.getCodersHelper().getItemStatuses().get(sveska.getStatus()).isLendable();
                }
                try {
                    itemAvailability = BisisApp.bisisService.getItemAvailability(ctlgno).execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (zaduziv) {
            if (!itemAvailability.getBorrowed()) {
                listContainsItem(itemAvailability);
                itemAvailability.setBorrowed(true);
                listOfItems.add(itemAvailability);
                record = getRecord(ctlgno);
            }
        }
        return record;
    }

    public void listContainsItem(ItemAvailability item) {
        Iterator<ItemAvailability> it = listOfItems.iterator();
        while (it.hasNext()) {
            ItemAvailability tmp = it.next();
            if (tmp.getCtlgNo().equals(item.getCtlgNo())) {
                listOfItems.remove(tmp);
                return;
            }
        }
    }

    public String returnBook(String ctlgno) {
        log.info("Razduzivanje primerka: " + ctlgno);
        try {
            primerak = BisisApp.bisisService.getPrimerakByInvNum(ctlgno).execute().body();
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
        if (primerak != null) {
            try {
                itemAvailability = BisisApp.bisisService.getItemAvailability(ctlgno).execute().body();
            } catch (IOException e) {
                log.error(e);
                e.printStackTrace();
            }
            listContainsItem(itemAvailability);
            if (itemAvailability.getBorrowed()) {
                itemAvailability.setBorrowed(false);
                listOfItems.add(itemAvailability);
            }
        } else {
            try {
                sveska = BisisApp.bisisService.getSveskaByInvNum(ctlgno).execute().body();
            } catch (IOException e) {
                log.error(e);
                e.printStackTrace();
            }
            if (sveska != null) {
                try {
                    itemAvailability = BisisApp.bisisService.getItemAvailability(ctlgno).execute().body();
                } catch (IOException e) {
                    log.error(e);
                    e.printStackTrace();
                }
                listContainsItem(itemAvailability);
                if (itemAvailability.getBorrowed()) {
                    itemAvailability.setBorrowed(false);
                    listOfItems.add(itemAvailability);
                }
            }
        }
        if (itemAvailability.getInventoryId() != null) {
            return itemAvailability.getCtlgNo();
        } else {
            return null;
        }

    }

    public String getErrorMessage() {
        String message = "";
        if (primerak == null && sveska == null) {
            message = "Nepostojeci inventarni broj!";
        } else if (primerak != null) {
            message = "Status primerka: ";
            if (primerak.getStatus() != null) {
                message = message + BisisApp.appConfig.getCodersHelper().getItemStatuses().get(primerak.getStatus()).getDescription() + ", ";
            }
            if (itemAvailability.getBorrowed()) {
                message = message + "Zauzet";
            } else {
                message = message + "Slobodan";
            }
            primerak = null;
        } else if (sveska != null) {
            message = "Status primerka: ";
            if (sveska.getStatus() != null) {
                message = message + BisisApp.appConfig.getCodersHelper().getItemStatuses().get(sveska.getStatus()).getDescription() + ", ";
            }
            if (itemAvailability.getBorrowed()) {
                message = message + "Zauzet";
            } else {
                message = message + "Slobodan";
            }
            sveska = null;
        }
        return message;
    }

    public boolean existBook() {
        if (primerak == null && sveska == null) {
            return false;
        } else {
            return true;
        }
    }

    public boolean chargedBook() {
        if (primerak != null) {
            if (primerak.getStatus() == null || BisisApp.appConfig.getCodersHelper().getItemStatuses().get(primerak.getStatus()).isLendable()) {
                return itemAvailability.getBorrowed();
            }
            return false;
        } else if (sveska != null) {
            if (sveska.getStatus() == null || BisisApp.appConfig.getCodersHelper().getItemStatuses().get(sveska.getStatus()).isLendable()) {
                return itemAvailability.getBorrowed();
            }
            return false;
        } else {
            return false;
        }
    }

    public boolean isInInventory() {
        return itemAvailability.getInventoryId() != null;
    }

    public List getListOfItems() {
        return listOfItems;
    }

    public List<ItemAvailability> getListOfBooksToBeReserved() {
        return booksToBeReserved;
    }

    public void releaseListOfItems() {
        listOfItems.clear();
    }

    public Record getRecord(String ctlgno) {
        Record record = null;
        try {
            record = BisisApp.bisisService.getRecordByCtlgNo(ctlgno).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return record;
    }

    public int getRecordId(String ctlgno) {
        Record record = getRecord(ctlgno);
        if (record != null) {
            return record.getRecordID();
        } else {
            return 0;
        }
    }

    public int getRecords(SearchModelCirc searchModel) {
        List<String> recordQueryResultIds = null;
        if (searchModel != null) {
            try {
                recordQueryResultIds = BisisApp.bisisService.searchBooks(searchModel).execute().body();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
//            CachingWrapperFilter filter = new CachingWrapperFilter(new BisisFilter(list));
//            res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(new MatchAllDocsQuery()),SerializationUtils.serialize(filter), "TI_sort");
//            resultList = list;
            //TODO list != null and searchModel = null
        }
        if (recordQueryResultIds == null) {
            return 0;
        } else if (recordQueryResultIds.size() == 0) {
            return 1;
        } else {
            if (!searchModel.getDepartments().isEmpty()) {
                Cirkulacija.getApp().getMainFrame().getSearchBooksResults().setFilter(searchModel.getDepartments().get(0));
            } else {
                Cirkulacija.getApp().getMainFrame().getSearchBooksResults().setFilter(null);
            }
            Cirkulacija.getApp().getMainFrame().getSearchBooksResults().setHits(recordQueryResultIds);
//            if (resultList != null)
//                Cirkulacija.getApp().getMainFrame().getSearchBooksResults().setCtlgnoNum(resultList.size());
            //TODO broj zaduzenih primeraka po tom upitu
            Cirkulacija.getApp().getMainFrame().getSearchBooksResults().setCtlgnoNum(0);
            Cirkulacija.getApp().getMainFrame().showPanel("searchBooksResultsPanel");
            return 2;
        }
    }


}

