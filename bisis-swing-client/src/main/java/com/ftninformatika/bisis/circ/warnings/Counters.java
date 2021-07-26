package com.ftninformatika.bisis.circ.warnings;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.WarningCounter;
import com.ftninformatika.bisis.circ.WarningType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class Counters {

    private HashMap counters = null;
    private WarningType wt = null;

    public Counters(List list, WarningType wt) {
        this.wt = wt;
        counters = new HashMap();
        Iterator it = list.iterator();
        WarningCounter wc = null;
        while (it.hasNext()) {
            wc = (WarningCounter) it.next();
            if (wc.getWarningType().equals(wt.getDescription())) {
                counters.put(wc.getWarnYear(), wc);
            }
        }
    }

    public int getNext(int year) {
        String key = Integer.toString(year);
        int value = 0;
        if (counters.containsKey(key)) {
            WarningCounter wc = (WarningCounter) counters.get(key);
            value = wc.getLastNo();
            value++;
            wc.setLastNo(value);
        } else {
            WarningCounter wc = new WarningCounter();
            wc.setWarningType(wt.getDescription());
            wc.setWarnYear(Integer.toString(year));
            wc.setLibrary(BisisApp.appConfig.getLibrary());
            wc.setLastNo(1);
            counters.put(key, wc);
            value = 1;
        }
        return value;
    }

    public void turnBack(int year) {
        Integer key = new Integer(year);
        int value;
        if (counters.containsKey(key)) {
            WarningCounter wc = (WarningCounter) counters.get(key);
            value = wc.getLastNo();
            value--;
            wc.setLastNo(value);
        }
    }

    public List<WarningCounter> getList() {
        List<WarningCounter> list = new ArrayList<WarningCounter>();
        Set set = counters.keySet();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Object cnt = it.next();
            String key = null;
            if (cnt instanceof Integer)
                key = cnt.toString();
            else if (cnt instanceof String)
                key = (String) cnt;
            if (key != null)
                list.add((WarningCounter) counters.get(key));
        }
        return list;
    }

}
