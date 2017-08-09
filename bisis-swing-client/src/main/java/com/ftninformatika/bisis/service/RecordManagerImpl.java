package com.ftninformatika.bisis.service;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.Record;
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
        return (Record[]) BisisApp.bisisService.getRecordsByIds(recIDs).execute().body().toArray();
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
    public List<String> selectExp(/*String query,*/ String prefix, String text) throws IOException {
        return BisisApp.bisisService.getExpand(prefix, text).execute().body();
    }

}
