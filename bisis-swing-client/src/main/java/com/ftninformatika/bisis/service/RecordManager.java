package com.ftninformatika.bisis.service;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.coders.Counter;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordResponseWrapper;
import com.ftninformatika.bisis.search.Result;
import com.ftninformatika.bisis.search.SearchModel;
import com.ftninformatika.bisis.search.SearchModelCirc;

import java.io.IOException;
import java.util.List;

/**
 * Created by Petar on 8/9/2017.
 */
public class RecordManager {


    public Result searchRecords(SearchModel searchModel) throws IOException {
        return BisisApp.bisisService.searchRecordsIdsResult(searchModel).execute().body();
    }

    public List<String> searchRecordsCirc(SearchModelCirc searchModel) throws IOException {
        return BisisApp.bisisService.searchRecordsIds(searchModel).execute().body();
    }


    public Record getRecord(String recID) throws IOException {
        return BisisApp.bisisService.getOneRecord(recID).execute().body();
    }

    public Record[] getRecords(List<String> recIDs) throws IOException {
        Record[] retVal = new Record[recIDs.size()];
        return  BisisApp.bisisService.getRecordsByIds(recIDs).execute().body().toArray(retVal);
    }

    public List<RecordResponseWrapper> getRecordsAllData(List<String> recIDs) throws IOException {
        return  BisisApp.bisisService.getRecordsAllDataByIds(recIDs).execute().body();
    }

    public Record getAndLock(String recID, String userId) throws IOException {
        return BisisApp.bisisService.getAndLockRecord(recID, userId).execute().body();
    }

    public String lock(String recID, String userId) throws IOException {
        return BisisApp.bisisService.lockRecord(recID, userId).execute().body();
    }

    public String unlock(String recID) throws IOException {
        return BisisApp.bisisService.unlockRecord(recID).execute().body();
    }

    public boolean add(Record rec) throws IOException {
        return BisisApp.bisisService.createRecord(rec).execute().body() != null;
    }

    public Record update(Record rec) {
        return null;
    }

    public boolean delete(String _id) throws IOException {
        return BisisApp.bisisService.deleteRecord(_id).execute().body();
    }

    public boolean reindex(String recID) {
        return false;
    }

    public List<String> selectExp( String prefix, String text) throws IOException {
        return BisisApp.bisisService.getExpand(prefix, text).execute().body();
    }


    public Integer getNewID(String counterName){
        Integer retVal = -1;
        if (BisisApp.appConfig.getCodersHelper().getCounters().containsKey(counterName)){
            try {
                retVal = BisisApp.bisisService.incrementCounter(counterName).execute().body();
                Counter c = BisisApp.appConfig.getCodersHelper().getCounters().get(counterName);
                c.setCounterValue(c.getCounterValue() + 1);
                BisisApp.appConfig.getCodersHelper().getCounters().put(counterName, c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            throw new RuntimeException("Nije pronadjen brojac: " + counterName);
        }
        return retVal;
    }

}
