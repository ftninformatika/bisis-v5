/**
 * 
 */
package com.ftninformatika.bisis.editor.groupinv;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingWorker;

/**
 * @author Administrator
 *
 */
public class LoadFileTask extends SwingWorker<String, String> {

	@Override
	protected String doInBackground() throws Exception {
		
		for(String broj:invNumbers){
			tableModel.addItem(broj);
			System.out.println(broj);
		}
		
		return "";
	}
	
	public GroupInvTableModel getTableModel() {
		return tableModel;
	}

	public void setTableModel(GroupInvTableModel tableModel) {
		this.tableModel = tableModel;
	}

	private List<String> invNumbers = new ArrayList<String>();
	private GroupInvTableModel tableModel = new GroupInvTableModel();
	

	public List<String> getInvNumbers() {
		return invNumbers;
	}

	public void setInvNumbers(List<String> invNumbers) {
		this.invNumbers = invNumbers;
	}
	
	

}
