package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.models.circ.Lending;
import com.ftninformatika.bisis.models.circ.pojo.UserCategory;
import com.ftninformatika.bisis.records.Record;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

public class LendingTableModel extends AbstractTableModel implements Serializable {

    protected ArrayList<Lending> dataView;
    protected List<String> columnIdentifiers;
    protected List<String> authors;
    protected List<String> titles;
    protected List<String> signatures;


    public LendingTableModel() {
      columnIdentifiers = new ArrayList<String>();
      columnIdentifiers.add(Messages.getString("circulation.acquisno")); //$NON-NLS-1$
      columnIdentifiers.add(Messages.getString("circulation.author")); //$NON-NLS-1$
      columnIdentifiers.add(Messages.getString("circulation.pubtitle")); //$NON-NLS-1$
      columnIdentifiers.add(Messages.getString("circulation.signature")); //$NON-NLS-1$
      columnIdentifiers.add("Datum zaduzenja"/*Messages.getString("circulation.chargingdate")*/); //$NON-NLS-1$
      columnIdentifiers.add("Datum produzenja"/*Messages.getString("circulation.renewdate")*/); //$NON-NLS-1$
      columnIdentifiers.add("Rok vracanja"/*Messages.getString("circulation.duedate")*/); //$NON-NLS-1$
      columnIdentifiers.add("Prekoracenje"/*Messages.getString("circulation.days")*/); //$NON-NLS-1$
      //columnIdentifiers.add("Lokacija");
      dataView = new ArrayList<Lending>();
      authors = new ArrayList<String>();
      titles = new ArrayList<String>();
      signatures = new ArrayList<String>();
    }

    public void setData(Set data){
      dataView = new ArrayList<Lending>();
      authors = new ArrayList<String>();
      titles = new ArrayList<String>();
      signatures = new ArrayList<String>();
      Iterator it = data.iterator();
      Lending tmp;
      
      while (it.hasNext()){
        tmp = (Lending)it.next();
        dataView.add(tmp);
        Record record = //Cirkulacija.getApp().getRecordsManager().getRecord(tmp.getCtlgNo());
						null;
        RecordBean bean = null;
        if (record != null){
          bean = new RecordBean(record);
          authors.add(bean.getAutor());
          titles.add(bean.getNaslov());
          signatures.add(bean.getSignatura(tmp.getCtlgNo()));
        } else {
          authors.add(""); //$NON-NLS-1$
          titles.add(""); //$NON-NLS-1$
          signatures.add(""); //$NON-NLS-1$
        }
      }
    	fireTableDataChanged();
    }
    
//		 Manipulating rows
    
	    public boolean addRow(String ctlgno, Record record, String location, UserCategory usrctg) {
	    	Iterator<Lending> it = dataView.iterator();
	    	while (it.hasNext()){
	    		Lending row = it.next();
	    		if (row.getCtlgNo().equals(ctlgno)){
	    			return true;
	    		}
	    	}
	    	int row = getRowCount();
	    	Lending rowData = new Lending();
	    	rowData.setCtlgNo(ctlgno);
	    	rowData.setLibrarianLend(Cirkulacija.getApp().getLibrarian().getUsername());
	    	rowData.setLendDate(new Date());
	        rowData.setLocation(location);
	        dataView.add(rowData);
	        Cirkulacija.getApp().getUserManager().addLending(rowData);
	        computeDeadline(new Date(), rowData, usrctg);
	        if (record != null){
	          RecordBean bean = new RecordBean(record);
	          authors.add(bean.getAutor());
	          titles.add(bean.getNaslov());
	          signatures.add(bean.getSignatura(ctlgno));
	        } else {
	          authors.add(""); //$NON-NLS-1$
	          titles.add(""); //$NON-NLS-1$
	          signatures.add(""); //$NON-NLS-1$
	        }
	    	fireTableRowsInserted(row, row);
	    	return false;
	    }


	    public void removeRows(int[] rows) {
	    	List<Integer> l = new ArrayList<Integer>();
	    	for (int i = 0; i < rows.length; i++){
	    		l.add(Integer.valueOf(rows[i]));
	    	}
	    	Collections.sort(l);
	        for (int i = l.size()-1; i >= 0; i--){
	          dataView.get(l.get(i).intValue()).setReturnDate(new Date());
	          dataView.get(l.get(i).intValue()).setLibrarianReturn(Cirkulacija.getApp().getLibrarian().getUsername());
	          Cirkulacija.getApp().getUserManager().updateLending(dataView.get(l.get(i).intValue()));
	          dataView.remove(l.get(i).intValue());
	          authors.remove(l.get(i).intValue());
	          titles.remove(l.get(i).intValue());
	          signatures.remove(l.get(i).intValue());
	  	      fireTableRowsDeleted(l.get(i).intValue(), l.get(i).intValue());
	        }
	    }
      
      public void updateRows(int[] rows, UserCategory usrctg){
      		List<Integer> l = new ArrayList<Integer>();
	    	for (int i = 0; i < rows.length; i++){
	    		l.add(Integer.valueOf(rows[i]));
	    	}
	    	Collections.sort(l);
	        for (int i = l.size()-1; i >= 0; i--){
	          dataView.get(l.get(i).intValue()).setResumeDate(new Date());
	          dataView.get(l.get(i).intValue()).setLibrarianResume(Cirkulacija.getApp().getLibrarian().getUsername());
	          computeDeadline(new Date(), dataView.get(l.get(i).intValue()), usrctg);
	          Cirkulacija.getApp().getUserManager().updateLending(dataView.get(l.get(i).intValue()));
	          fireTableRowsUpdated(l.get(i).intValue(), l.get(i).intValue());
	        }
      }
      
      public List getRow(int i){
        List list = new ArrayList();
        list.add(dataView.get(i));
        list.add(authors.get(i));
        list.add(titles.get(i));
        list.add(signatures.get(i));
        return list;
      }
      
      public void setRow(List list){
        dataView.add((Lending)list.get(0));
        authors.add((String)list.get(1));
        titles.add((String)list.get(2));
        signatures.add((String)list.get(3));
      }


		//
//		 Implementing the TableModel interface
		//

	    public int getRowCount() {
	        return dataView.size();
	    }

	    public int getColumnCount() {
	        return columnIdentifiers.size();
	    }

	    public String getColumnName(int column) {
	        Object id = null; 
		// This test is to cover the case when 
		// getColumnCount has been subclassed by mistake ... 
		if (column < columnIdentifiers.size()) {  
		    id = columnIdentifiers.get(column); 
		}
	        return (id == null) ? super.getColumnName(column) 
	                            : id.toString();
	    }

	    public boolean isCellEditable(int row, int column) {
	        return false;
	    }

	    public Object getValueAt(int row, int column) {
	        Lending rowData = (Lending)dataView.get(row);
	        switch (column){
	        	case 0: return rowData.getCtlgNo();
	        	case 1: return authors.get(row); 
	        	case 2: return titles.get(row);
	        	case 3: return signatures.get(row);
	        	case 4: return rowData.getLendDate();
	        	case 5: return rowData.getResumeDate();
	        	case 6: return rowData.getDeadline();
	        	case 7: return computeDays(rowData.getDeadline());
            //case 7: return rowData.getLocation();
	        	default: return null;
	        }
	    }

	    public void setValueAt(Object aValue, int row, int column) {
	        
	    }

	   public Class getColumnClass(int col) {
			switch (col){
			 case 0: return String.class;
			 case 1: return String.class;
			 case 2: return String.class;
			 case 3: return String.class;
			 case 4: return Date.class;
			 case 5: return Date.class;
			 case 6: return Date.class;
       //case 7: return Location.class;
			 default: return String.class;
			}
	   }
      
      
	    private void computeDeadline(Date date, Lending rowData, UserCategory usrctg){
        
        try {
        	Integer period = usrctg.getPeriod();
        	Integer max_period = usrctg.getMaxPeriod();
        	
        	Calendar deadline = Calendar.getInstance();
            deadline.setTime(date);
		    deadline.add(Calendar.DATE, period);
		      
		    Calendar max_date = Calendar.getInstance();
		    max_date.setTime(rowData.getLendDate());
		    max_date.add(Calendar.DATE, max_period);
		    
		    if (max_date.before(deadline)){
		    	rowData.setDeadline(max_date.getTime());
		    } else {
		    	rowData.setDeadline(deadline.getTime());
		    }

        } catch (Exception e) {
         
        }
        
      }  
	    
	  private String computeDays(Date deadline){
		  Date today = new Date();
		  String days = "";
		  if (deadline.before(today)){
			 days = String.valueOf((today.getTime() - deadline.getTime()) / (1000 * 60 * 60 * 24));
		  }
		  return days;
	  }
		  
}
