package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.circ.pojo.PictureBook;
import com.ftninformatika.utils.Messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class PicturebooksTableModel extends AbstractTableModel implements Serializable {


    protected List<PictureBook> data;
    protected List<String> columnIdentifiers;

    public PicturebooksTableModel() {
        columnIdentifiers = new ArrayList<String>();
        columnIdentifiers.add(Messages.getString("circulation.date"));
        columnIdentifiers.add(Messages.getString("circulation.checkout2"));
        columnIdentifiers.add(Messages.getString("circulation.discharge2"));
        columnIdentifiers.add(Messages.getString("circulation.state"));
        data = new ArrayList<PictureBook>();
    }

    public void setData(List data) {
//        this.data.clear();
//        Iterator it = data.iterator();
//        while (it.hasNext()) {
//            this.data.add((PictureBook) it.next());
//        }
        this.data = data;
        fireTableDataChanged();
    }

//		 Manipulating rows

    public void addRow(Date sdate, int lendNo, int returnNo, int state) {
        int row = getRowCount();
        PictureBook rowData = new PictureBook();
        rowData.setLendDate(sdate);
        rowData.setLendNo(lendNo);
        rowData.setReturnNo(returnNo);
        rowData.setStatus(state);
        //Cirkulacija.getApp().getUserManager().addPicturebooks(rowData);
        data.add(rowData);
        fireTableRowsInserted(row, row);
    }

    //
//		 Implementing the TableModel interface
    //

    public int getRowCount() {
        return data.size();
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

    public void setColumnName(int column, String text) {
        if (column < columnIdentifiers.size()) {
            columnIdentifiers.set(column, text);
            fireTableStructureChanged();
        }
    }

    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public Object getValueAt(int row, int column) {
        PictureBook rowData = (PictureBook) data.get(row);
        switch (column) {
            case 0:
                return rowData.getLendDate();
            case 1:
                return rowData.getLendNo();
            case 2:
                return rowData.getReturnNo();
            case 3:
                return rowData.getStatus();
            default:
                return null;
        }
    }

    public void setValueAt(Object aValue, int row, int column) {

    }

    public Class getColumnClass(int col) {
        switch (col) {
            case 0:
                return Date.class;
            case 1:
                return Integer.class;
            case 2:
                return Integer.class;
            case 3:
                return Integer.class;
            default:
                return String.class;
        }
    }
}
