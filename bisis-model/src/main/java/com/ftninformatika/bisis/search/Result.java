package com.ftninformatika.bisis.search;

import java.util.List;

/**
 * Created by Petar on 1/17/2018.
 * Klasa rezultata pretrage po ugledu na BISIS4
 */
public class Result {
    private String[] records;
    private List<String> invs;

    public Result(){
    }


    public List<String> getInvs() {
        return invs;
    }
    public void setInvs(List<String> invs) {
        this.invs = invs;
    }
    public String[] getRecords() {
        return records;
    }
    public void setRecords(String[] records) {
        this.records = records;
    }

    public int getResultCount(){
        if (records == null)
            return 0;
        return records.length;
    }
}
