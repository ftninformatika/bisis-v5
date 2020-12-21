package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.UserCategory;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.reservations.ReservationOnProfile;
import com.ftninformatika.bisis.reservations.ReservationStatus;
import com.ftninformatika.utils.Messages;

import javax.swing.table.AbstractTableModel;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * @author marijakovacevic
 */
public class ReservationTableModel extends AbstractTableModel implements Serializable {

    protected List<ReservationOnProfile> dataView;
    protected List<String> columnIdentifiers;
    protected List<String> authors;
    protected List<String> titles;
    protected List<String> signatures;

    public ReservationTableModel() {
        columnIdentifiers = new ArrayList<>();
        columnIdentifiers.add(Messages.getString("circulation.acquisno")); //$NON-NLS-1$
        columnIdentifiers.add(Messages.getString("circulation.author")); //$NON-NLS-1$
        columnIdentifiers.add(Messages.getString("circulation.pubtitle")); //$NON-NLS-1$
        columnIdentifiers.add(Messages.getString("circulation.signature")); //$NON-NLS-1$
        columnIdentifiers.add(Messages.getString("circulation.reservationDate")); //$NON-NLS-1$
        columnIdentifiers.add(Messages.getString("circulation.reservationDueDate")); //$NON-NLS-1$
        columnIdentifiers.add(Messages.getString("circulation.location"));
        columnIdentifiers.add(Messages.getString("STATUS"));
        dataView = new ArrayList<>();
        authors = new ArrayList<>();
        titles = new ArrayList<>();
        signatures = new ArrayList<>();
    }


    public void setData(List<ReservationOnProfile> data) {
        dataView = new ArrayList<>();
        authors = new ArrayList<>();
        titles = new ArrayList<>();
        signatures = new ArrayList<>();
        Iterator<ReservationOnProfile> it = data.iterator();
        ReservationOnProfile tmp;

        while (it.hasNext()) {
            tmp = it.next();
            dataView.add(tmp);
            Record record = null;
//            if (tmp.getCtlgNo() == null || tmp.getCtlgNo().equals("")) {
//                record = Cirkulacija.getApp().getRecordsManager().getRecord(tmp.getCtlgNo());
//            } else {
            try {
                record = BisisApp.getRecordManager().getRecord(tmp.getRecord_id());
            } catch (IOException e) {
                e.printStackTrace();
            }
            RecordBean bean = null;
            if (record != null) {
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

    public boolean addRow(String ctlgno, Record record, String location, UserCategory usrctg, String userId) {
        Iterator<ReservationOnProfile> it = dataView.iterator();
        while (it.hasNext()) {
            ReservationOnProfile row = it.next();
            if (row.getCtlgNo().equals(ctlgno)) {
                return true;
            }
        }
        int row = getRowCount();
        ReservationOnProfile rowData = new ReservationOnProfile();
        dataView.add(rowData);
        Cirkulacija.getApp().getUserManager().addReservation(rowData);
        if (record != null) {
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

    public List getRow(int i) {
        List list = new ArrayList();
        list.add(dataView.get(i));
        list.add(authors.get(i));
        list.add(titles.get(i));
        list.add(signatures.get(i));
        return list;
    }

    public void setRow(List list) {
        dataView.add((ReservationOnProfile) list.get(0));
        authors.add((String) list.get(1));
        titles.add((String) list.get(2));
        signatures.add((String) list.get(3));
    }


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

    public Object getValueAt(int row, int column) {
        ReservationOnProfile rowData = dataView.get(row);
        switch (column) {
            case 0:
                return rowData.getCtlgNo() == null || rowData.getCtlgNo().equals("") ? "-" : rowData.getCtlgNo();
            case 1:
                return authors.get(row);
            case 2:
                return titles.get(row);
            case 3:
                return signatures.get(row) == null || signatures.get(row).equals("") ? "-" : signatures.get(row);
            case 4:
                return rowData.getReservationDate();
            case 5:
                return rowData.getPickUpDeadline() == null ? "-" : rowData.getPickUpDeadline();
            case 6:
                return rowData.getCoderId();
            case 7:
                return getReservationStatus(rowData.getReservationStatus());
            default:
                return null;
        }
    }


    public String getReservationStatus(ReservationStatus status) {
        if (status.equals(ReservationStatus.ASSIGNED_BOOK)) {
            return Messages.getString("circulation.assigned").toUpperCase();
        } else {
            return Messages.getString("circulation.onWait").toUpperCase();
        }
    }

    public ReservationOnProfile getItem(int row) {
        return dataView.get(row);
    }

    public Class getColumnClass(int col) {
        switch (col) {
            case 4:
            case 5:
                return Date.class;
            case 6:
            default:
                return String.class;
        }
    }

}