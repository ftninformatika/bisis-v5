package com.ftninformatika.bisis.editor.registries;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.registry.*;

import java.io.IOException;
import java.util.*;
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
        fireTableDataChanged();
    }

    public void deleteRow(int index) {
        Registry reg =(Registry) getRegByRowIndex(index).get();
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
        Optional toUpdate = getRegByRowIndex(index);

        if (this.registryType == Registries.ODREDNICE) {
            ((RegPrOd) toUpdate.get()).setPojam(item.getText1());
        }
        else
            if (this.registryType == Registries.PODODREDNICE)
                ((RegPrPod) toUpdate.get()).setPojam(item.getText1());
        else
            if (this.registryType == Registries.KOLEKTIVNI)
                ((RegKolOdr) toUpdate.get()).setKolektivac(item.getText1());
        else
            if (this.registryType == Registries.ZBIRKE)
            ((RegZbirke) toUpdate.get()).setNaziv(item.getText1());
        else
            if (this.registryType == Registries.UDK) {
            ((RegUDKSubgroup) toUpdate.get()).setGrupa(item.getText1());
            ((RegUDKSubgroup) toUpdate.get()).setOpis(item.getText2());
        }
        else
            if (this.registryType == Registries.AUTORI) {
            ((RegAutOdr) toUpdate.get()).setAutor(item.getText1());
            ((RegAutOdr) toUpdate.get()).setOriginal(item.getText2());
        }
        try {
            RegistryManager.updateRegistry((Registry)toUpdate.get());
        } catch (IOException e) {
            e.printStackTrace();
        }

        rows.set(index, item);
        fireTableRowsUpdated(index, index);

    }

    public Optional<? extends Registry> getRegByRowIndex(int index){
        String id = ((RegistryItem)rows.get(index)).get_id();
        switch (registryType) {
            case Registries.ODREDNICE: {
                return
                        castCollection(results, RegPrOd.class).stream()
                                .filter(o -> o.get_id().equals(id)).findFirst();

            }
            case Registries.PODODREDNICE: {
                return
                        castCollection(results, RegPrPod.class).stream()
                                .filter(o -> o.get_id().equals(id)).findFirst();

            }
            case Registries.AUTORI: {
                return
                        castCollection(results, RegAutOdr.class).stream()
                                .filter(o -> o.get_id().equals(id)).findFirst();

            }
            case Registries.KOLEKTIVNI: {
                return
                        castCollection(results, RegKolOdr.class).stream()
                                .filter(o -> o.get_id().equals(id)).findFirst();

            }
            case Registries.UDK: {
                return
                        castCollection(results, RegUDKSubgroup.class).stream()
                                .filter(o -> o.get_id().equals(id)).findFirst();

            }
            case Registries.ZBIRKE: {
                return
                        castCollection(results, RegZbirke.class).stream()
                                .filter(o -> o.get_id().equals(id)).findFirst();

            }
        }
        return null;
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

    public <T>List<T> castCollection(List srcList, Class<T> clas){
        List<T> list =new ArrayList<T>();
        for (Object obj : srcList) {
            if(obj!=null && clas.isAssignableFrom(obj.getClass()))
                list.add(clas.cast(obj));
        }
        return list;
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

                if (results.get(i) instanceof RegPrOd) {
                    item.setText1(((RegPrOd) results.get(i)).getPojam());
                    item.set_id(((RegPrOd) results.get(i)).get_id());
                } else
                if (results.get(i) instanceof RegPrPod) {
                    item.setText1(((RegPrPod) results.get(i)).getPojam());
                    item.set_id(((RegPrPod) results.get(i)).get_id());
                } else
                if (results.get(i) instanceof RegKolOdr) {
                    item.setText1(((RegKolOdr) results.get(i)).getKolektivac());
                    item.set_id(((RegKolOdr) results.get(i)).get_id());
                } else
                if (results.get(i) instanceof RegZbirke) {
                    item.setText1(((RegZbirke) results.get(i)).getNaziv());
                    item.set_id(((RegZbirke) results.get(i)).get_id());
                } else
                if (results.get(i) instanceof RegUDKSubgroup) {
                    item.setText1(((RegUDKSubgroup) results.get(i)).getGrupa());
                    item.setText2(((RegUDKSubgroup) results.get(i)).getOpis());
                    item.set_id(((RegUDKSubgroup) results.get(i)).get_id());
                } else
                if (results.get(i) instanceof RegAutOdr) {
                    item.setText1(((RegAutOdr) results.get(i)).getAutor());
                    item.setText2(((RegAutOdr) results.get(i)).getOriginal());
                    item.set_id(((RegAutOdr) results.get(i)).get_id());
                }
                rows.add(item);
            }
            dlg.progressBar.setString("сортирам");
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
        } else

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
        } else

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
        } else

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
        } else

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
        } else

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
        } else

        if (registryType == Registries.NEPOZNAT) {
            results = new ArrayList<>();
        }
    }

    private int registryType;
    private int columnCount;
    private Vector rows = new Vector();
    private List<Registry> results;

}
