/**
 * 
 */
package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.utils.Messages;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;



/**
 * @author dimicb
 *
 */

public class LibrarianTableModel extends AbstractTableModel {

	private List<LibrarianDB> libList = new ArrayList<>();
	private String[] columns;
	
	private String[] defaultPrefixes = {"AU","TI","PU","PY","KW"}; 
	
	public LibrarianTableModel() {
		super();
		libList = LibEnvProxy.getAllLibrarians();
		if(libList!=null){
			columns = new String[5];
			columns[0]=Messages.getString("LibrarianEnvironment.USERNAME");
			columns[1]=Messages.getString("LibrarianEnvironment.FIRST_NAME");
			columns[2]=Messages.getString("LibrarianEnvironment.LAST_NAME");
			columns[3]=Messages.getString("LibrarianEnvironment.E-MAIL");
			columns[4]=Messages.getString("LibrarianEnvironment.ROLES");
		}else{
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("LIBENV_DB_ERROR"), Messages.getString("LIBENV_ERROR"),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}
		
	}
	
	public void initializeModel(){
		libList = LibEnvProxy.getAllLibrarians();	
		if(libList!=null)
			fireTableDataChanged();
		else
			JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("LIBENV_DB_ERROR"), Messages.getString("LIBENV_ERROR"),JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		}

	public int getColumnCount() {
		return columns.length;
	}

	
	public int getRowCount() {		
		return libList.size();
	}
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		LibrarianDB lib = libList.get(rowIndex);
		switch(columnIndex){
		case 0: return lib.getUsername();
		case 1: return lib.getIme();
		case 2: return lib.getPrezime();
		case 3: return lib.getEmail();
		case 4: return getUlogeString(lib);		
		}
		return null;
	}
	
	public String getColumnName(int column){
		return columns[column];
	}	
	
	public LibrarianDB getRow(int rowIndex){
		return libList.get(rowIndex);
	}
	
	public boolean updateLibrarian(LibrarianDB lib){
		boolean updateSuccesful = false;
		if(lib.getContext().getPref1()==null)
			setDefaultPrefixes(lib);
		int index = -1;
		for(int i=0;i<libList.size();i++){			
			if(libList.get(i).getUsername().equals(lib.getUsername())){
				index = i;
				break;
			}
		}
		if(index==-1){
			//insert
			libList.add(lib);
			updateSuccesful = LibEnvProxy.addLibrarian(lib);
		}else{
			//update
			lib.set_id(libList.get(index).get_id());
			libList.set(index, lib);
			updateSuccesful = LibEnvProxy.updateLibrarian(lib);
		}
		if(updateSuccesful)
			fireTableDataChanged();
		return updateSuccesful;
	}
	
	public boolean deleteLibrarian(int index){
		LibrarianDB lib = libList.get(index);
		libList.remove(index);
		fireTableDataChanged();
		return LibEnvProxy.deleteLibrarian(lib);		
	}
	
	public boolean deleteLibrarian(LibrarianDB lib){
		boolean deleted = LibEnvProxy.deleteLibrarian(lib);
		if(deleted){
			libList.remove(lib);
			fireTableDataChanged();
		}
		return deleted ;		
	}
	
	private String getUlogeString(LibrarianDB lib) {
		StringBuffer ret = new StringBuffer();
		if(lib.hasRole(Librarian.Role.ADMINISTRACIJA))
			ret.append("A");
		if(lib.hasRole(Librarian.Role.OBRADA)){
			if(!ret.toString().equals("")) ret.append(",");
			ret.append("O");
		}
		if(lib.hasRole(Librarian.Role.CIRKULACIJA)){
			if(!ret.toString().equals("")) ret.append(",");
			ret.append("C");
		}	
		return ret.toString();		
	}
	
	private void setDefaultPrefixes(LibrarianDB lib){
		lib.getContext().setPref1(defaultPrefixes[0]);
		lib.getContext().setPref2(defaultPrefixes[1]);
		lib.getContext().setPref3(defaultPrefixes[2]);
		lib.getContext().setPref4(defaultPrefixes[3]);
		lib.getContext().setPref5(defaultPrefixes[4]);
	}
	
	
	
	
}
