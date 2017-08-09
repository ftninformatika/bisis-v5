package com.ftninformatika.bisis.hitlist;

import javax.swing.AbstractListModel;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.service.BisisService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;


public class HitListModel extends AbstractListModel {

  public HitListModel() {
  }
  
  public Object getElementAt(int index) {
    try {
      return records[index];
    } catch (Exception ex) {
      log.fatal(ex);
      ex.printStackTrace();
      return null;
    }
  }
  
  public int getSize() {
    if (records == null)
      return 0;
    return records.length;
  }
  
  public void setHits(/*int[] recIDs*/ Record[] recs) {

    this.records = recs;

      for (int i =0;i<recs.length;i++) {
        if (records[i] != null)
          records[i].pack();
      }
      fireContentsChanged(this, 0, records.length - 1);

  }
  
  public void refresh() {
    fireContentsChanged(this, 0, records.length - 1);
  }  
  
  public boolean remove(int index){
  	/*if(BisisApp.isFileMgrEnabled()){
	  	if(FileManagerClient.deleteAllForRecord(BisisApp.getFileManagerURL(), records[index].getRecordID())){
	  		boolean deleted = BisisApp.getRecordManager().delete(records[index].getRecordID());
	  		if(deleted)
	  			records[index] = null;
	  		return deleted;
	  	}else
	  			return false;
	  	}else{
	  		boolean deleted = BisisApp.recMgr).delete(records[index].getRecordID());
	  		if(deleted)
	  			records[index] = null;
	  		return deleted;
	  	}*/
    Long resp = null;
    try {
      resp = BisisApp.bisisService.deleteRecordByRecId(records[index].getRecordID()).execute().body(); //TODO- ovo i ostale operacije pozivanja resta objediniti i spakovati na jedno mesto
    } catch (IOException e) {
      e.printStackTrace();
    }

    if ( resp != null && resp == 1) {
      //ArrayUtils.removeElement(records, records[index]);
      records[index] = null;
      return true;
    }
    return false;
  }  
  
  private Record[] records; //promenjeno u drugi models radi testiranja hitlist frame-a
  
  private static Log log = LogFactory.getLog(HitListModel.class);
}
