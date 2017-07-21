package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.librarian.ProcessType;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;


public class ProcessTypeListModel extends AbstractListModel {
	
	private List<ProcessType> procTypeList = new ArrayList<ProcessType>();
	private ProcessType defaultProcessType;
	
	public ProcessTypeListModel(List<ProcessType> procTypeList, 
			ProcessType defaultProcessType){	
		if(procTypeList==null)
			this.procTypeList = new ArrayList<ProcessType>();
		else
			this.procTypeList = procTypeList;
		this.defaultProcessType = defaultProcessType;
	}

	public Object getElementAt(int index) {		
		return procTypeList.get(index);
	}

	public int getSize() {
		if(procTypeList!=null)
			return procTypeList.size();
		return 0;
	}

	public void setProcTypeList(List<ProcessType> procTypeList) {
		this.procTypeList = procTypeList;
		fireDataChanged();
	}
	
	public void fireDataChanged(){
		if(procTypeList!=null)
			fireContentsChanged(this, 0, procTypeList.size());		 
			
	}
	
	public List<ProcessType> getProcessTypes(){
		return procTypeList;
	}

	public ProcessType getDefaultProcessType() {
		return defaultProcessType;
	}

	public void setDefaultProcessType(ProcessType defaultProcessType) {
		this.defaultProcessType = defaultProcessType;
		fireDataChanged();
	}
	
	public boolean addProcessType(ProcessType pt){		
		boolean added = procTypeList.add(pt);
		fireDataChanged();		
		return added;
	}
	
	public boolean containsProcessType(ProcessType pt){		
		for(ProcessType ptIt:procTypeList)
				if(ptIt.getName().equals(pt.getName()))
					return true;		
		return false;		
	}
	
	public void deleteProcessType(int index){
		procTypeList.remove(index);
		fireDataChanged();
		
	}
	
}
