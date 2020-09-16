/**
 *
 */
package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.librarian.ProcessType;
import com.ftninformatika.utils.Messages;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

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
    public ProcessTypeTableModel() {
        procTypeList = LibEnvProxy.getAllProcTypes();
        if (procTypeList != null) {
            initializeColumnNames();
        } else {
            JOptionPane.showMessageDialog(BisisApp.getMainFrame(), Messages.getString("LIBENV_DB_ERROR"), Messages.getString("LIBENV_ERROR"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
        }
    }

    public int getColumnCount() {
        return columns.length;
    }

    public int getRowCount() {
        if (procTypeList != null)
            return procTypeList.size();
        else return 0;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        ProcessType pt = procTypeList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return pt.getName();
            case 1:
                return pt.getPubType().getName();
        }
        return null;
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    public ProcessType getRow(int index) {
        return procTypeList.get(index);
    }

    public ProcessType updateProcessType(ProcessType pt) {
        ProcessType savedProcessType = null;
        if (procTypeList.stream().anyMatch(processType ->
                (processType.getName().equals(pt.getName())))) {
            savedProcessType = LibEnvProxy.saveProcessType(pt);
        } else {
            pt.setLibName(BisisApp.appConfig.getLibrary());
            savedProcessType = LibEnvProxy.saveProcessType(pt);
            procTypeList.add(savedProcessType);
        }
        if (savedProcessType != null) {
            fireTableDataChanged();
            return savedProcessType;
        }
        return null;

    }

    public boolean deleteProcessType(int index) {
        ProcessType pt = procTypeList.get(index);
        boolean deleted = LibEnvProxy.deleteProcessType(pt);
        if (deleted) {
            procTypeList.remove(index);
            fireTableDataChanged();
        }
        return deleted;
    }

    public boolean deleteProcessType(ProcessType pt) {
        boolean deleted = LibEnvProxy.deleteProcessType(pt);
        if (deleted) {
            procTypeList.remove(pt);
            fireTableDataChanged();
        }
        return deleted;
    }

    private void initializeColumnNames() {
        columns = new String[2];
        columns[0] = Messages.getString("ProcessTypeTableModel.PROCESSING_TYPE_NAME"); //$NON-NLS-1$
        columns[1] = Messages.getString("ProcessTypeTableModel.PUBLICATION_TYPE"); //$NON-NLS-1$
    }
}
