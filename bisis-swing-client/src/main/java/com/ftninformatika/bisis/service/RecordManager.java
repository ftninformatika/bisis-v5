package com.ftninformatika.bisis.service;

import com.ftninformatika.bisis.records.LockException;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.search.SearchModel;

import java.io.IOException;
import java.util.List;

/**
 * Created by Petar on 8/9/2017.
 */
public interface RecordManager {
    // finding records
    public List<String> select1(SearchModel searchModel) throws IOException;
    /*public int[] select2(Query query, String sortPrefix);
    public int[] select2x(byte[] serializedQuery, String sortPrefix);
    public int[] select3(Query query, Filter filter, String sortPrefix);
    public Result selectAll1(String query, String sortPrefix) throws ParseException;
    public Result selectAll2(Query query, String sortPrefix);
    public Result selectAll2x(byte[] serializedQuery, String sortPrefix);
    public Result selectAll3(Query query, Filter filter, String sortPrefix);
    public Result selectAll3x(byte[] serializedQuery, byte[] serializedFilter, String sortPrefix);*/
    public List<String> selectExp(/*String query,*/ String prefix,String text) throws IOException;

    // retrieving records
    public Record getRecord(String recID) throws IOException;
    //public List<DocFile> getDocFiles(int rn);
    public Record[] getRecords(List<String> recIDs) throws IOException;
    public Record getAndLock(String recID, String userId) throws IOException, LockException;
    public String lock(String recID, String userId) throws IOException;
    public String unlock(String recID) throws IOException;

    // storing records
    //public int getNewID(String counterName);
    public boolean add(Record rec) throws IOException;
    public Record update(Record rec);
    public boolean delete(String recID) throws IOException;
    public boolean reindex(String recID);

}
