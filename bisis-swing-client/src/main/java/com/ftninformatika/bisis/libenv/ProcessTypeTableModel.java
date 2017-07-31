/**
 * 
 */
package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.librarian.ProcessType;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 * @author dimicb
 *
 */
public class ProcessTypeTableModel extends AbstractTableModel {

	private String[] columns;	
	private List<ProcessType> procTypeList = new ArrayList<ProcessType>();
	
	/**
	 * ucitava sve tipove obrade u models
	 */
	public ProcessTypeTableModel(){
		procTypeList = LibEnvProxy.getAllProcTypes();
		if(procTypeList!=null){		
			initializeColumnNames();
		}else{
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Greska u komunikciji sa bazom podataka!","Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}
	}
	
	public ProcessTypeTableModel(List<ProcessType> procTypeList){
		this.procTypeList = procTypeList;
		initializeColumnNames();		
	}
	
	public void initializeModel(){
		procTypeList = LibEnvProxy.getAllProcTypes();
		if(procTypeList!=null)
			fireTableDataChanged();
		else
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(),"Greska u komunikciji sa bazom podataka!","Gre\u0161ka",JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
	}
	
	public int getColumnCount() {		
		return columns.length;
	}
	
	public int getRowCount() {		
		if(procTypeList!=null)
			return procTypeList.size();
		else return 0;
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		ProcessType pt = procTypeList.get(rowIndex);
		switch(columnIndex){
		case 0: return pt.getName();
		case 1: return pt.getPubType().getName();
		}
		return null;
	}
	
	public String getColumnName(int column){
		return columns[column];
	}	
	
	public ProcessType getRow(int index){
		return procTypeList.get(index);
	}
	
	public boolean updateProcessType(ProcessType pt){	
		boolean successful = false;
		int index = -1;
		for(int i=0;i<procTypeList.size();i++){
			if(procTypeList.get(i).getName().equals(pt.getName())){				
				index = i;
				break;
			}
		}
		if(index==-1){
			//insert
			successful = LibEnvProxy.addProcessType(pt);
			procTypeList.add(pt);
		}else{
			//update
			pt.setId(procTypeList.get(index).getId());
			procTypeList.set(index, pt);
			successful = LibEnvProxy.updateProcessType(pt);
		}		
		if(successful)
			fireTableDataChanged();
		return successful;
			
	}	
	
	public boolean deleteProcessType(int index){	
		ProcessType pt = procTypeList.get(index);
		boolean deleted = LibEnvProxy.deleteProcessType(pt);
		if(deleted){			
			procTypeList.remove(index);
			fireTableDataChanged();
		}
		return deleted;
	}
	
	public boolean deleteProcessType(ProcessType pt){		
		boolean deleted = LibEnvProxy.deleteProcessType(pt);		
		if(deleted){
			procTypeList.remove(pt);
			fireTableDataChanged();
		}
		return deleted;
	}	
	
	private void initializeColumnNames(){
		columns = new String[2];
		columns[0] = Messages.getString("ProcessTypeTableModel.PROCESSING_TYPE_NAME"); //$NON-NLS-1$
		columns[1] = Messages.getString("ProcessTypeTableModel.PUBLICATION_TYPE"); //$NON-NLS-1$
	}
}
