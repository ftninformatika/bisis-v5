package com.ftninformatika.bisis.editor.registries;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.registry.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;


public class RegistryTableModel extends AbstractTableModel {

    public RegistryTableModel(int registryType) {
        this.registryType = registryType;
        if (registryType == Registries.AUTORI || registryType == Registries.UDK)
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
    }

    public void deleteRow(int index) {
        try {
            RegistryManager.deleteRegistry(results.get(index));
        } catch (IOException e) {
            e.printStackTrace();
        }
        rows.remove(index);
    }

    public void updateRow(int index, RegistryItem item) {
        Registry toUpdate = results.get(index);

        if (this.registryType == Registries.ODREDNICE)
            ((RegPrOd) toUpdate).setPojam(item.getText1());
        if (this.registryType == Registries.PODODREDNICE)
            ((RegPrPod) toUpdate).setPojam(item.getText1());
        if (this.registryType == Registries.KOLEKTIVNI)
            ((RegKolOdr) toUpdate).setKolektivac(item.getText1());
        if (this.registryType == Registries.ZBIRKE)
            ((RegZbirke) toUpdate).setNaziv(item.getText1());
        if (this.registryType == Registries.UDK) {
            ((RegUDKSubgroup) toUpdate).setGrupa(item.getText1());
            ((RegUDKSubgroup) toUpdate).setOpis(item.getText2());
        }
        if (this.registryType == Registries.AUTORI) {
            ((RegAutOdr) toUpdate).setAutor(item.getText1());
            ((RegAutOdr) toUpdate).setOriginal(item.getText2());
        }
        try {
            RegistryManager.updateRegistry(toUpdate);
        } catch (IOException e) {
            e.printStackTrace();
        }

        rows.set(index, item);
        fireTableRowsUpdated(index, index);

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
            dlg.progressBar.setString("u\u010ditavam podatke");
            dlg.progressBar.setVisible(true);

            initResults(dlg.getCurrentType());
            for (int i = 0; i < results.size(); i++) {
                dlg.progressBar.setValue(i + 1);
                RegistryItem item = new RegistryItem();
                item.setIndex(i);

                if (results.get(i) instanceof RegPrOd) {
                    item.setText1(((RegPrOd) results.get(i)).getPojam());
                }
                if (results.get(i) instanceof RegPrPod) {
                    item.setText1(((RegPrPod) results.get(i)).getPojam());
                }
                if (results.get(i) instanceof RegKolOdr) {
                    item.setText1(((RegKolOdr) results.get(i)).getKolektivac());
                }
                if (results.get(i) instanceof RegZbirke) {
                    item.setText1(((RegZbirke) results.get(i)).getNaziv());
                }
                if (results.get(i) instanceof RegUDKSubgroup) {
                    item.setText1(((RegUDKSubgroup) results.get(i)).getGrupa());
                    item.setText2(((RegUDKSubgroup) results.get(i)).getOpis());
                }
                if (results.get(i) instanceof RegAutOdr) {
                    item.setText1(((RegAutOdr) results.get(i)).getAutor());
                    item.setText2(((RegAutOdr) results.get(i)).getOriginal());
                }

                rows.add(item);
            }
            dlg.progressBar.setString("sortiram");
            sort(RegistryUtils.getLatComparator());
            dlg.progressBar.setVisible(false);
            dlg.getGlassPane().setVisible(false);
        }

    }

    public void initResults(int registryType) {
        results = new ArrayList<>();
        if (registryType == Registries.ODREDNICE) {
            List<RegPrOd> response = null;
            try {
                response = BisisApp.bisisService.getRegPrOd().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response == null)
                return;
            for (RegPrOd r : response)
                results.add(r);
        }

        if (registryType == Registries.PODODREDNICE) {
            List<RegPrPod> response = null;
            try {
                response = BisisApp.bisisService.getRegPrPod().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response == null)
                return;
            for (RegPrPod r : response)
                results.add(r);
        }

        if (registryType == Registries.KOLEKTIVNI) {
            List<RegKolOdr> response = null;
            try {
                response = BisisApp.bisisService.getRegKolOdr().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response == null)
                return;
            for (RegKolOdr r : response)
                results.add(r);
        }

        if (registryType == Registries.ZBIRKE) {
            List<RegZbirke> response = null;
            try {
                response = BisisApp.bisisService.getRegZbirke().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response == null)
                return;
            for (RegZbirke r : response)
                results.add(r);
        }

        if (registryType == Registries.UDK) {
            List<RegUDKSubgroup> response = null;
            try {
                response = BisisApp.bisisService.getRegUDKS().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response == null)
                return;
            for (RegUDKSubgroup r : response)
                results.add(r);
        }

        if (registryType == Registries.AUTORI) {
            List<RegAutOdr> response = null;
            try {
                response = BisisApp.bisisService.getRegAuthor().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(response == null)
                return;
            for (RegAutOdr r : response)
                results.add(r);
        }
    }

    private int registryType;
    private int columnCount;
    private Vector rows = new Vector();
    private List<Registry> results;

}
