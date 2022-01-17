package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.wrappers.MemberData;
import com.ftninformatika.bisis.librarian.Librarian;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.circ.Member;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.table.AbstractTableModel;


public class SearchUsersTableModel extends AbstractTableModel implements Serializable {

    protected List<Member> data;
    protected List<String> columnIdentifiers;
    private Set<String> selected;

    public SearchUsersTableModel() {
        columnIdentifiers = new ArrayList<String>();
        columnIdentifiers.add(Messages.getString("circulation.usernumber"));
        columnIdentifiers.add(Messages.getString("circulation.firstname"));
        columnIdentifiers.add(Messages.getString("circulation.lastname"));
        columnIdentifiers.add(Messages.getString("circulation.parentname"));
        columnIdentifiers.add(Messages.getString("circulation.umcn"));
        columnIdentifiers.add(Messages.getString("circulation.place"));
        columnIdentifiers.add(Messages.getString("circulation.address"));
        columnIdentifiers.add(Messages.getString("circulation.email"));
        if (BisisApp.appConfig.getLibrarian().hasRole(Librarian.Role.ADMINISTRACIJA)) {
            columnIdentifiers.add(Messages.getString("circulation.select"));
        }
        data = new ArrayList<Member>();
        selected = new HashSet<>();
    }

    public String getColumnName(int column) {
        Object id = null;
        if (column < columnIdentifiers.size()) {
            id = columnIdentifiers.get(column);
        }
        return (id == null) ? super.getColumnName(column) : id.toString();
    }

    public void setData(List data) {
        this.data = data;
        selected = new HashSet<>();
        fireTableDataChanged();
    }

    public void removeAll() {
        data.clear();
        selected.clear();
        fireTableDataChanged();
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return columnIdentifiers.size();
    }

    public Class getColumnClass(int col) {
        if (col == 8) {
            return Boolean.class;
        }
        return String.class;
    }

    public boolean isCellEditable(int row, int column) {
        if (column == 8) {
            return true;
        }
        return false;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Member rowData = (Member) data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowData.getUserId();
            case 1:
                return rowData.getFirstName();
            case 2:
                return rowData.getLastName();
            case 3:
                return rowData.getParentName();
            case 4:
                return rowData.getJmbg();
            case 5:
                return rowData.getCity();
            case 6:
                return rowData.getAddress();
            case 7:
                return rowData.getEmail();
            case 8:
                if (selected.contains(rowData.getUserId())) {
                    return true;
                } else {
                    return false;
                }
            default:
                return null;
        }
        //			return data.get(rowIndex)[columnIndex];
    }

    public void setValueAt(Object value, int row, int col) {
        if (col == 8) {
            String userId = data.get(row).getUserId();
            if ((Boolean)value) {
                selected.add(userId);
            } else {
                selected.remove(userId);
            }
            fireTableCellUpdated(row, col);
        }
    }

    public String getUser(int rowIndex) {
        return data.get(rowIndex).getUserId();
    }


    public List<String> getSelectedUsers() {
        return new ArrayList<>(selected);
    }
}
	 
