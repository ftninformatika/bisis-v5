package com.ftninformatika.bisis.editor.registries;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.registry.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.table.AbstractTableModel;


public class RegistryTableModel extends AbstractTableModel {

    public RegistryTableModel(int registryType) {
        this.registryType = registryType;
        if (registryType == Registries.AUTORI || registryType == Registries.UDK || registryType == Registries.IZDAVACI)
            columnCount = 2;
        else
            columnCount = 1;
    }

    public void init(RegistryDlg dlg) {
        final RegistryDlg dlg2 = dlg;
        dlg.getGlassPane().setVisible(true);
        final com.ftninformatika.utils.SwingWorker worker = new com.ftninformatika.utils.SwingWorker() {
            public Object construct() {
                return new InitTask(RegistryTableModel.this, dlg2);
            }
        };
        worker.start();
    }

    public int getRowCount() {
        return rows.size();
    }

    public Object getValueAt(int row, int col) {
        RegistryItem item = (RegistryItem) rows.get(row);
        if (item == null)
            return "";
        if (col == 0)
            return item.getText1();
        else
            return item.getText2();
    }

    public int getColumnCount() {
        return columnCount;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public void clear() {
        rows.clear();
    }

    public RegistryItem getRow(int index) {
        return (RegistryItem) rows.get(index);
    }

    public void setRow(int index, RegistryItem item) {
        rows.set(index, item);
    }

    public void addRow(RegistryItem item) {
        try {
            results.add(RegistryManager.addRegistry(item, this.registryType));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rows.add(item);
        fireTableDataChanged();
    }

    public void deleteRow(int index) {
        GenericRegistry reg = getRegByRowIndex(index);
        try {
            RegistryManager.deleteRegistry(reg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        results.remove(index);
        rows.remove(index);
    }

    public void updateRow(int index, RegistryItem item) {
        //Registry toUpdate = results.get(index);
        GenericRegistry toUpdate = getRegByRowIndex(index);
        toUpdate.setField1(item.getText1());

        if (registryType == Registries.UDK || registryType == Registries.AUTORI || registryType == Registries.IZDAVACI){
            toUpdate.setField2(item.getText2());
        }

        try {
            RegistryManager.updateRegistry(toUpdate);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rows.set(index, item);
        fireTableRowsUpdated(index, index);

    }

    public GenericRegistry getRegByRowIndex(int index){
        String id = ((RegistryItem)rows.get(index)).get_id();

        return results.stream().filter(r -> r.get_id().equals(id)).collect(Collectors.toList()).get(0);
    }

    public int searchRow(RegistryItem item) {
        for (int i = 0; i < rows.size(); i++) {
            RegistryItem ri = getRow(i);
            if (ri.isLike(item))
                return i;
        }
        return -1;
    }

    public void sort(Comparator comparator) {
        Collections.sort(rows, comparator);
        fireTableDataChanged();
    }

    public int getRegistryType() {
        return registryType;
    }

    public ArrayList<ArrayList<Object>> getResults() {
        ArrayList<ArrayList<Object>> retVal = new ArrayList<>();
        for (Object o : results) {
            ArrayList<Object> li = (ArrayList<Object>) o;
            retVal.add(li);
        }
        return retVal;
    }

    public class InitTask {
        public InitTask(RegistryTableModel model, RegistryDlg dlg) {

            dlg.progressBar.setMinimum(0);
            dlg.progressBar.setMaximum(100);
            dlg.progressBar.setString("учитавам податке");
            dlg.progressBar.setVisible(true);

            initResults(dlg.getCurrentType());
            for (int i = 0; i < results.size(); i++) {
                dlg.progressBar.setValue(i + 1);
                RegistryItem item = new RegistryItem();
                item.setIndex(i);

                if (dlg.getCurrentType() == Registries.AUTORI || dlg.getCurrentType() == Registries.UDK || dlg.getCurrentType() == Registries.IZDAVACI){
                    item.setText1(results.get(i).getField1());
                    item.setText2(results.get(i).getField2());
                    item.set_id(results.get(i).get_id());
                }
                else {
                    item.setText1(results.get(i).getField1());
                    item.set_id(results.get(i).get_id());
                }

                rows.add(item);
            }
            dlg.progressBar.setString("сортирам");
            sort(RegistryUtils.getCyrComparator());
            dlg.progressBar.setVisible(false);
            dlg.getGlassPane().setVisible(false);
        }

    }

    public void initResults(int registryType) {
        results = new ArrayList<>();
        try {
            results = BisisApp.bisisService.getRegistriesForType(registryType).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int registryType;
    private int columnCount;
    private Vector rows = new Vector();
    private List<GenericRegistry> results;

}
