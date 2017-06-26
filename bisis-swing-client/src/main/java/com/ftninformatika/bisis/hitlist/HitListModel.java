package com.ftninformatika.bisis.hitlist;

import javax.swing.AbstractListModel;

import com.ftninformatika.bisis.records.Record;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



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
  
  public void setHits(/*int[] recIDs*/ Record oneRec) {
   /* try {
      records = BisisApp.getRecordManager().getRecords(recIDs);      
      for (int i =0;i<recIDs.length;i++){
      	if(records[i]!=null)
      		records[i].pack();      	
      }
      fireContentsChanged(this, 0, records.length - 1);
    } catch (Exception ex) {
      log.fatal(ex);     
    }*/
    records = new Record[] { oneRec };
   //records[0].pack(); without packing
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
	  		boolean deleted = BisisApp.getRecordManager().delete(records[index].getRecordID());
	  		if(deleted)
	  			records[index] = null;
	  		return deleted;
	  	}
  		*/
  	return false;
  }  
  
  private Record[] records; //promenjeno u drugi model radi testiranja hitlist frame-a
  
  private static Log log = LogFactory.getLog(HitListModel.class);
}
