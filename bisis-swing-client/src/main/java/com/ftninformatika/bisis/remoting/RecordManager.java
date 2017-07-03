package com.ftninformatika.bisis.remoting;

/**
 * Created by Petar on 7/3/2017.
 */
        import com.ftninformatika.bisis.records.Record;

        import java.util.List;


public interface RecordManager {

    // finding records
    //public int[] select1(String query, String sortPrefix)throws ParseException;
    //public int[] select2(Query query, String sortPrefix);
    public int[] select2x(byte[] serializedQuery, String sortPrefix);
    //public int[] select3(Query query, Filter filter, String sortPrefix);
    //public Result selectAll1(String query, String sortPrefix) throws ParseException;
    //public Result selectAll2(Query query, String sortPrefix);
    //public Result selectAll2x(byte[] serializedQuery, String sortPrefix);
    //public Result selectAll3(Query query, Filter filter, String sortPrefix);
    //public Result selectAll3x(byte[] serializedQuery, byte[] serializedFilter, String sortPrefix);
    //public List<String> selectExp(String query, String prefix,String text);

    // retrieving records
    public Record getRecord(int recID);
    //public List<DocFile> getDocFiles(int rn);
    public Record[] getRecords(int[] recIDs);
    //public Record getAndLock(int recID, String user) throws LockException;
    public String lock(int recID, String user);
    public void unlock(int recID);

    // storing records
    public int getNewID(String counterName);
    public boolean add(Record rec);
    public Record update(Record rec);
    public boolean delete(int recID);
    public boolean reindex(int recID);

}

