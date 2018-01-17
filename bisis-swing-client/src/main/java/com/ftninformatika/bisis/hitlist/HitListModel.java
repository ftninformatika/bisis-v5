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
import java.util.Arrays;
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
  
  public void setHits(String[] recIDs) {

    try {
      records = BisisApp.recMgr.getRecords(Arrays.asList(recIDs));
      for (int i =0;i<recIDs.length;i++){
        if(records[i]!=null)
          records[i].pack();
      }
      fireContentsChanged(this, 0, records.length - 1);
    } catch (Exception ex) {
      log.fatal(ex);
    }

  }
  
  public void refresh() {
    fireContentsChanged(this, 0, records.length - 1);
  }

  public boolean remove(int index){
    if(/*BisisApp.isFileMgrEnabled()*/true){
      if(/*FileManagerClient.deleteAllForRecord(BisisApp.getFileManagerURL(), records[index].get_id())*/true){
        boolean deleted = false;
        try {
          deleted = BisisApp.recMgr.delete(records[index].get_id());
        } catch (IOException e) {
          e.printStackTrace();
        }
        if(deleted)
          records[index] = null;
        return deleted;
      }else
        return false;
    }else{
      boolean deleted = false;
      try {
        deleted = BisisApp.recMgr.delete(records[index].get_id());
      } catch (IOException e) {
        e.printStackTrace();
      }
      if(deleted)
        records[index] = null;
      return deleted;
    }

  }

  private Record[] records; //promenjeno u drugi models radi testiranja hitlist frame-a
  
  private static Log log = LogFactory.getLog(HitListModel.class);
}
