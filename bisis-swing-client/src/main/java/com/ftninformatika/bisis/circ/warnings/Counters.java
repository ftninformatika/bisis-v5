package com.ftninformatika.bisis.circ.warnings;

import com.ftninformatika.bisis.models.circ.WarningCounter;
import com.ftninformatika.bisis.models.circ.WarningType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class Counters {
	
	private HashMap counters = null;
  private WarningType wt = null;
	
	public Counters(List list, WarningType wt){
		this.wt = wt;
		counters = new HashMap();
		Iterator it = list.iterator();
		WarningCounter wc = null;
		while (it.hasNext()){
			wc = (WarningCounter)it.next();
			counters.put(wc.getWarnYear(), wc);
    }
	}
	
	public int getNext(int year){
	  	Integer key = new Integer(year);
	  	int value = 0;
	  	if (counters.containsKey(key)){
            WarningCounter wc = (WarningCounter)counters.get(key);
  	     value = wc.getLastNo();
  	     value++;
         wc.setLastNo(value);
	  	}else{
            WarningCounter wc = new WarningCounter();
         wc.setWarningType(wt.getDescription());
         wc.setWarnYear(Integer.toString(year));
         wc.setLastNo(1);
	  		 counters.put(key, wc);
	  		 value = 1;
	  	}
	  	return value;
	}
	
	public void turnBack(int year){
		Integer key = new Integer(year);
		int value;
		if (counters.containsKey(key)){
            WarningCounter wc = (WarningCounter)counters.get(key);
       value = wc.getLastNo();
       value--;
       wc.setLastNo(value);
		}
	}
	
	public List<WarningCounter> getList(){
      List<WarningCounter> list = new ArrayList<WarningCounter>();
			Set set = counters.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()){
				Integer key = (Integer)it.next();
				list.add((WarningCounter)counters.get(key));
      }
      return list;
  }

}
