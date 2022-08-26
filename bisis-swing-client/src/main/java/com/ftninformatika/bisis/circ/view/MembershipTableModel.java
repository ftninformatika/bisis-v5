package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.pojo.MembershipType;
import com.ftninformatika.bisis.circ.pojo.Signing;
import com.ftninformatika.bisis.circ.pojo.UserCategory;
import com.ftninformatika.utils.Messages;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MembershipTableModel extends AbstractTableModel implements Serializable {


    protected List<Signing> dataView;
    protected List<String> columnIdentifiers;
    private Signing newRow;

    public MembershipTableModel() {
        columnIdentifiers = new ArrayList<String>();
        columnIdentifiers.add(Messages.getString("circulation.location")); //$NON-NLS-1$
        columnIdentifiers.add(Messages.getString("circulation.begindate")); //$NON-NLS-1$
        columnIdentifiers.add(Messages.getString("circulation.expirdate")); //$NON-NLS-1$
        columnIdentifiers.add(Messages.getString("circulation.cost")); //$NON-NLS-1$
        columnIdentifiers.add(Messages.getString("circulation.receipt")); //$NON-NLS-1$
        dataView = new ArrayList<Signing>();
    }

    public void setData(List<Signing> data) {
//      this.dataView.clear();
//      Iterator it = data.iterator();
//      while (it.hasNext()){
//        this.dataView.add((Signing)it.next());
//      }
        this.dataView = data;
        fireTableDataChanged();
    }

//		 Manipulating rows

    public void addRow(String location) {
        int row = getRowCount();
        Signing rowData = new Signing();
        rowData.setLibrarian(Cirkulacija.getApp().getLibrarian().getUsername());
        rowData.setSignDate(new Date());
        rowData.setLocation(location);
        this.newRow = rowData;
        this.dataView.add(rowData);
        fireTableRowsInserted(row, row);
    }


    public void removeRow(int row) {
        Signing sig = dataView.remove(row);
        fireTableRowsDeleted(row, row);
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

    public void setColumnName(int column, String text) {
        if (column < columnIdentifiers.size()) {
            columnIdentifiers.set(column, text);
            fireTableStructureChanged();
        }
    }

    public boolean isCellEditable(int row, int column) {
        if (this.newRow != null && row == this.dataView.indexOf(this.newRow)) {
            return true;
        } else {
            return false;
        }
    }

    public Object getValueAt(int row, int column) {
        Signing rowData = dataView.get(row);
        switch (column) {
            case 0:
                return rowData.getLocation();
            case 1:
                return rowData.getSignDate();
            case 2:
                return rowData.getUntilDate();
            case 3:
                return rowData.getCost();
            case 4:
                return rowData.getReceipt();
            default:
                return null;
        }
    }

    public void setValueAt(Object aValue, int row, int column) {
        Signing rowData = dataView.get(row);
        switch (column) {
            case 0:
                rowData.setLocation((String) aValue);
                break;
            case 1:
                rowData.setSignDate((Date) aValue);
                break;
            case 2:
                rowData.setUntilDate((Date) aValue);
                break;
            case 3:
                rowData.setCost((Double) aValue);
                break;
            case 4:
                rowData.setReceipt((String) aValue);
        }
        fireTableCellUpdated(row, column);
    }

    public Class getColumnClass(int col) {
        switch (col) {
            case 0:
                return String.class;
            case 1:
                return Date.class;
            case 2:
                return Date.class;
            case 3:
                return Double.class;
            case 4:
                return String.class;
            default:
                return String.class;
        }
    }

    public void computeMmbrship(MembershipType mt, UserCategory uc) {
        Double cost = Cirkulacija.getApp().getUserManager().getMembership(mt.getDescription(), uc.getDescription());
        setValueAt(cost, getRowCount() - 1, 3);
    }

    public void computePeriod(Integer period) {
        Date startDate = (Date) getValueAt(getRowCount() - 1, 1);
        Calendar cl = Calendar.getInstance();
        cl.setTime(startDate);
        cl.add(Calendar.DATE, period);
        setValueAt(cl.getTime(), getRowCount() - 1, 2);
    }


    public static class CellRenderer extends DefaultTableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int col) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

            if (value instanceof Date) {
                String strDate = new SimpleDateFormat("dd.MM.yyyy.").format((Date) value);
                this.setText(strDate);
            }

            return this;
        }
    }

    public static class CellEditor extends DefaultCellEditor {

        public CellEditor() {
            super(new JTextField());
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row,
                                                     int column) {
            Component comp = super.getTableCellEditorComponent(table, value, isSelected, row, column);
            if (value instanceof Date) {
                String strDate = new SimpleDateFormat("dd.MM.yyyy.").format((Date) value);
                ((JTextField)comp).setText(strDate);
            }
            return comp;
        }

        public Object getCellEditorValue() {
            try {
                if (((JTextField)getComponent()).getText().trim().length() == 0)
                    return null;

                SimpleDateFormat fm = new SimpleDateFormat("dd.MM.yyyy.");
                Date date = fm.parse(((JTextField)getComponent()).getText().trim());
                return date;
            } catch (ParseException e) {
                return ((JTextField)getComponent()).getText();
            }
        }

        public boolean stopCellEditing() {
            String s = (String) super.getCellEditorValue();
            Object value;
            if ("".equals(s)) {
                value = null;
                return super.stopCellEditing();
            }
            try {
                SimpleDateFormat lFormat = new SimpleDateFormat("dd.MM.yyyy.");
                value = lFormat.parse(s);
            } catch (ParseException e) {
                ((JComponent) getComponent()).setBorder(new LineBorder(Color.red));
                return false;
            }
            return super.stopCellEditing();
        }


    }
}
