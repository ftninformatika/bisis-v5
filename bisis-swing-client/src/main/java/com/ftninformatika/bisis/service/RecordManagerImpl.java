package com.ftninformatika.bisis.service;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordResponseWrapper;
import com.ftninformatika.bisis.search.SearchModel;

import java.io.IOException;
import java.util.List;

/**
 * Created by Petar on 8/9/2017.
 */
public class RecordManagerImpl implements RecordManager {


    @Override
    public List<String> select1(SearchModel searchModel) throws IOException {
        return BisisApp.bisisService.searchRecordsIds(searchModel).execute().body();
    }


    @Override
    public Record getRecord(String recID) throws IOException {
        return BisisApp.bisisService.getOneRecord(recID).execute().body();
    }

    @Override
    public Record[] getRecords(List<String> recIDs) throws IOException {
        Record[] retVal = new Record[recIDs.size()];
        return  BisisApp.bisisService.getRecordsByIds(recIDs).execute().body().toArray(retVal);
    }

    @Override
    public List<RecordResponseWrapper> getRecordsAllData(List<String> recIDs) throws IOException {
        return  BisisApp.bisisService.getRecordsAllDataByIds(recIDs).execute().body();
    }


    @Override
    public Record getAndLock(String recID, String userId) throws IOException {
        return BisisApp.bisisService.getAndLockRecord(recID, userId).execute().body();
    }

    @Override
    public String lock(String recID, String userId) throws IOException {
        return BisisApp.bisisService.lockRecord(recID, userId).execute().body();
    }

    @Override
    public String unlock(String recID) throws IOException {
        return BisisApp.bisisService.unlockRecord(recID).execute().body();
    }

    @Override
    public boolean add(Record rec) throws IOException {
        return BisisApp.bisisService.createRecord(rec).execute().body() != null;
    }

    @Override
    public Record update(Record rec) {
        return null;
    }

    @Override
    public boolean delete(String recID) throws IOException {
        return BisisApp.bisisService.deleteRecord(recID).execute().body();
    }

    @Override
    public boolean reindex(String recID) {
        return false;
    }

    @Override
    public List<String> selectExp(/*String query,*/ String prefix, String text) throws IOException {
        return BisisApp.bisisService.getExpand(prefix, text).execute().body();
    }

}
