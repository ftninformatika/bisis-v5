package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.circ.pojo.Warning;
import com.ftninformatika.utils.Messages;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;


public class WarningsTableModel extends AbstractTableModel {

    private static final int COL_COUNT = 6;
    private String[] columnNames = {Messages.getString("circulation.remindernumber"), Messages.getString("circulation.remindertype"), Messages.getString("circulation.senddate"), Messages.getString("circulation.duedate"), Messages.getString("circulation.acquisitionnumber"), Messages.getString("circulation.note")}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
    private List data = null;


    public WarningsTableModel() {
        data = new ArrayList();
    }

    public void setData(List data) {
        try {
            this.data = data;
            fireTableDataChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void resetModel() {
        data.clear();
        fireTableDataChanged();
    }

    public int getColumnCount() {
        return COL_COUNT;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        Warning warning = (Warning) data.get(row);
        switch (col) {
            case 0:
                return warning.getWarnNo();
            case 1:
                return warning.getWarningType();
            case 2:
                return warning.getWarningDate();
            case 3:
                return warning.getDeadline();
            case 4:
                return null;//warning.getLending().getCtlgNo(); //TODO-????
            case 5:
                return warning.getNote();
            default:
                return null;
        }

    }

    public void setValueAt(Object aValue, int row, int col) {
        if (col == 5) {
            Warning warning = (Warning) data.get(row);
            warning.setNote((String) aValue);
            fireTableCellUpdated(row, col);
            //Cirkulacija.getApp().getUserManager().updateWarning(warning);
        }
    }

    public Class getColumnClass(int col) {
        switch (col) {
            case 0:
                return String.class;
            case 1:
                return String.class;
            case 2:
                return Date.class;
            case 3:
                return Date.class;
            case 4:
                return String.class;
            case 5:
                return String.class;
            default:
                return String.class;
        }
    }

    public boolean isCellEditable(int row, int col) {
        if (col == 5) {
            return true;
        }
        return false;
    }

}
