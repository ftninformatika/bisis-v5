package com.ftninformatika.bisis.editor.groupinv;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.inventar.InventarConstraints;
import com.ftninformatika.bisis.format.UItem;
import com.ftninformatika.bisis.records.Godina;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.Sveska;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.string.Signature;

import java.io.IOException;
import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


public class GroupInvTableModel extends AbstractTableModel {

    private List<Primerak> primerci = new ArrayList<Primerak>();
    private List<Godina> godine = new ArrayList<Godina>();
    private List<Sveska> sveske = new ArrayList<Sveska>();
    private List<String> neispravni = new ArrayList<String>();

    private List sviInventarni = new ArrayList();

    private String[] columns;
    private String[] columnSet;

    public GroupInvTableModel() {
        super();
        columns = new String[10];
        columns[0] = Messages.getString("INV_NUM");
        columns[1] = Messages.getString("SIGNATURE");
        columns[2] = Messages.getString("LOCATION");
        columns[3] = Messages.getString("STATUS");
        columns[4] = Messages.getString("NOTE");
        columns[5] = Messages.getString("UNIT_TYPE");
        columns[6] = Messages.getString("TITLE");
        columns[7] = Messages.getString("SUBLOCATION");
        columns[8] = Messages.getString("UDK");
        columns[9] = Messages.getString("INTERNAL_MARK");
    }

    public int getColumnCount() {
        return columns.length;
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    public int getRowCount() {
        return primerci.size() + godine.size() + sveske.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        int brojPrimeraka = primerci.size();
        int brojGodina = godine.size();
        if (columnIndex == 6) return GroupInvRecordUtils.getTitle(rowIndex);
        if (rowIndex < brojPrimeraka)
            return getValueForColumn(primerci.get(rowIndex), columnIndex);
        else if (rowIndex >= brojPrimeraka && rowIndex < brojPrimeraka + brojGodina)
            return getValueForColumn(godine.get(rowIndex - brojPrimeraka), columnIndex);
        else
            return getValueForColumn(sveske.get(rowIndex - brojPrimeraka - brojGodina), columnIndex);
    }

    public void deleteRow(int rowIndex) {
        String invNum = String.valueOf(getValueAt(rowIndex, 0));
        boolean deleted = false;


        for (Primerak p: primerci) {
            if (p.getInvBroj().equals(invNum)){
                primerci.remove(p);
                deleted = true;
                break;
            }
        }

        if (!deleted)
            for (Godina g: godine) {
                if (g.getInvBroj().equals(invNum)){
                    godine.remove(g);
                    deleted = true;
                    break;
                }
            }

        if (!deleted)
            for (Sveska s: sveske) {
                if (s.getInvBroj().equals(invNum)){
                    sveske.remove(s);
                    deleted = true;
                    break;
                }
            }

        if (deleted) {
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
    }

    public Object getValueForColumn(Object o, int column) {
        if (o instanceof Primerak) {
            if (column == 0) return ((Primerak) o).getInvBroj();
            if (column == 1) return Signature.format((Primerak) o);
            if (column == 2) return ((Primerak) o).getOdeljenje();
            if (column == 3) return ((Primerak) o).getStatus();
            if (column == 4) return ((Primerak) o).getNapomene();
            if (column == 5) return Messages.getString("ITEM_996");
            if (column == 7) return ((Primerak) o).getSigPodlokacija();
            if (column == 8) return ((Primerak) o).getSigUDK();
            if (column == 9) return ((Primerak) o).getSigIntOznaka();
        }
        if (o instanceof Godina) {
            if (column == 0) return ((Godina) o).getInvBroj();
            if (column == 1) return Signature.format((Godina) o);
            if (column == 2) return ((Godina) o).getOdeljenje();
            if (column == 3) return "";
            if (column == 4) return ((Godina) o).getNapomene();
            if (column == 5) return Messages.getString("YEAR_997");
            if (column == 7) return ((Godina) o).getSigPodlokacija();
            if (column == 8) return ((Godina) o).getSigUDK();
            if (column == 9) return ((Godina) o).getSigIntOznaka();
        }
        if (o instanceof Sveska) {
            if (column == 0) return ((Sveska) o).getInvBroj();
            if (column == 1) return ((Sveska) o).getSignatura();
            if (column == 2) return "";
            if (column == 3) return ((Sveska) o).getStatus();
            if (column == 4) return "";
            if (column == 5) return Messages.getString("NOTEBOOK_COLUMN");
            if (column == 7) return "";
        }
        return null;
    }

    /*
     * 	vraca da li je primerak, godina ili sveska
     * 	uspesno dodata u tabelu
     */

    public void addItem(String invBroj) {
        if (invBrojExists(invBroj)) return;
        Object item = GroupInvRecordUtils.loadItem(invBroj);
        if (item != null) {
            if (item instanceof Primerak) {
                primerci.add((Primerak) item);
                fireTableDataChanged();
            } else if (item instanceof Godina) {
                godine.add((Godina) item);
                fireTableDataChanged();
            } else if (item instanceof Sveska) {
                sveske.add((Sveska) item);
                fireTableDataChanged();
            }
        } else {
            neispravni.add(invBroj);
        }

    }

    /*
     * vraca sifarnik za selektovanu kolonu
     */

    public List<UItem> getCodes(int column) {
        if (columns[column].equals(Messages.getString("STATUS")))
            return BisisApp.appConfig.getCodersHelper().getCoderUItemList(BisisApp.appConfig.getCodersHelper().STATUS_CODER);
        else if (columns[column].equals(Messages.getString("LOCATION")))
            return BisisApp.appConfig.getCodersHelper().getCoderUItemList(BisisApp.appConfig.getCodersHelper().ODELJENJE_CODER);
        else if (columns[column].equals(Messages.getString("SUBLOCATION")))
            return BisisApp.appConfig.getCodersHelper().getCoderUItemList(BisisApp.appConfig.getCodersHelper().PODLOKACIJA_CODER);
        else if (columns[column].equals(Messages.getString("INTERNAL_MARK")))
            return BisisApp.appConfig.getCodersHelper().getCoderUItemList(BisisApp.appConfig.getCodersHelper().INTERNAOZNAKA_CODER);
        return null;
    }

    public void changeCode(String newCode, int selectedColumn, String datumStatusa) {
        if (columns[selectedColumn].equals(Messages.getString("STATUS"))) {
            for (Primerak p : primerci) {
                p.setStatus(newCode);
                if (datumStatusa != null)
                    try {
                        p.setDatumStatusa(InventarConstraints.sdf.parse(datumStatusa));
                    } catch (ParseException e) {
                    }
            }
            for (Sveska s : sveske) {
                s.setStatus(newCode);
                if (datumStatusa != null)
                    try {
                        s.setDatumStatusa(InventarConstraints.sdf.parse(datumStatusa));
                    } catch (ParseException e) {
                    }
            }
        }
        if (columns[selectedColumn].equals(Messages.getString("LOCATION"))) {
            for (Primerak p : primerci)
                p.setOdeljenje(newCode);
            for (Godina g : godine)
                g.setOdeljenje(newCode);

        }
        if (columns[selectedColumn].equals(Messages.getString("SUBLOCATION"))) {
            for (Primerak p : primerci)
                p.setSigPodlokacija(newCode);
            for (Godina g : godine)
                g.setSigPodlokacija(newCode);
        }
        if (columns[selectedColumn].equals(Messages.getString("INTERNAL_MARK"))) {
            for (Primerak p : primerci)
                p.setSigIntOznaka(newCode);
            for (Godina g : godine)
                g.setSigIntOznaka(newCode);
        }
        fireTableDataChanged();
    }

    public void changeText(String newText, int selectedColumn) {
        if (columns[selectedColumn].equals(Messages.getString("NOTE"))) {
            for (Primerak p : primerci) {
                p.setNapomene(newText);
            }
            for (Godina g : godine)
                g.setNapomene(newText);
        }
        if (columns[selectedColumn].equals(Messages.getString("UDK"))){
            for (Primerak p : primerci) {
                p.setSigUDK(newText);
            }
            for (Godina g : godine)
                g.setSigUDK(newText);
        }
        fireTableDataChanged();
    }

    public void appendText(String newText, int selectedColumn) {
        if (columns[selectedColumn].equals(Messages.getString("NOTE"))) {
            for (Primerak p : primerci) {
                String oldText = p.getNapomene() == null ? "" : p.getNapomene();
                p.setNapomene(oldText + newText);
            }
            for (Godina g : godine) {
                String oldText = g.getNapomene() == null ? "" : g.getNapomene();
                g.setNapomene(oldText + newText);
            }
        }
        if (columns[selectedColumn].equals(Messages.getString("UDK"))){
            for (Primerak p : primerci) {
                String oldText = p.getNapomene() == null ? "" : p.getNapomene();
                p.setSigUDK(oldText + newText);
            }
            for (Godina g : godine) {
                String oldText = g.getNapomene() == null ? "" : g.getNapomene();
                g.setSigUDK(oldText + newText);
            }
        }
        fireTableDataChanged();
    }

    public void clearList() {
        primerci.clear();
        godine.clear();
        sveske.clear();
        GroupInvRecordUtils.clearTitles();
        fireTableDataChanged();
    }

    public boolean columnCanBeSelected(int column) {
        return columns[column].equals(Messages.getString("STATUS")) ||
                columns[column].equals(Messages.getString("LOCATION")) ||
                columns[column].equals(Messages.getString("SUBLOCATION")) ||
                columns[column].equals(Messages.getString("UDK")) ||
                columns[column].equals(Messages.getString("INTERNAL_MARK")) ||
                columns[column].equals(Messages.getString("NOTE"));

    }

    public boolean invBrojExists(String invBroj) {
        for (Primerak p : primerci)
            if (p.getInvBroj().equals(invBroj))
                return true;
        for (Godina g : godine)
            if (g.getInvBroj().equals(invBroj))
                return true;
        for (Sveska s : sveske)
            if (s.getInvBroj().equals(invBroj))
                return true;
        return false;
    }


    public boolean updateRecords() {
        boolean ok = true;
        List<Record> records = new ArrayList<Record>();
        for (Primerak p : primerci) {
            Record rec = GroupInvRecordUtils.getRecordForInv(p.getInvBroj());
            boolean postoji = false;
            for (Record r : records) {
                if (r.getRecordID() == rec.getRecordID()) {
                    postoji = true;
                    rec = r;
                    break;
                }
            }
            if (!postoji) {
                records.add(rec);
            }
            Primerak stari = rec.getPrimerak(p.getInvBroj());
            rec.getPrimerci().remove(stari);
            rec.getPrimerci().add(p);

        }

        for (Godina g : godine) {
            Record rec = GroupInvRecordUtils.getRecordForInv(g.getInvBroj());
            boolean postoji = false;
            for (Record r : records) {
                if (r.getRecordID() == rec.getRecordID()) {
                    postoji = true;
                    rec = r;
                    break;
                }
            }
            if (!postoji) {
                records.add(rec);
            }
            Godina stara = rec.getGodina(g.getInvBroj());
            rec.getGodine().remove(stara);
            rec.getGodine().add(g);
        }

        for (Sveska s : sveske) {
            Record rec = GroupInvRecordUtils.getRecordForInv(s.getInvBroj());
            boolean postoji = false;
            for (Record r : records) {
                if (r.getRecordID() == rec.getRecordID()) {
                    postoji = true;
                    rec = r;
                    break;
                }
            }
            if (!postoji) {
                records.add(rec);
            }
            Godina g = rec.getGodinaForInvBRSveske(s.getInvBroj());
            Sveska stara = g.getSveska(s.getInvBroj());
            g.getSveske().remove(stara);
            g.getSveske().add(s);
        }


        for (Record record : records) {
            try {
                BisisApp.getRecordManager().update(record);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return ok;
    }

    public List<String> getNeispravni() {
        return neispravni;
    }

    public boolean isCodedColimn(int col) {
        if (col == 2 || col == 3 || col == 7 || col == 9) return true;
        return false;
    }


}
