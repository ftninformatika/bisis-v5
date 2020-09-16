/**
 *
 */
package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.USubfield;
import com.ftninformatika.utils.Messages;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author dimicb
 *
 */
public class ProcessTypeSubfieldsTableModel extends AbstractTableModel {

    private List<USubfield> initialSubfields = new ArrayList<USubfield>();
    private List<USubfield> mandatorySubfields = new ArrayList<USubfield>();
    String[] columns;

    public ProcessTypeSubfieldsTableModel() {
        columns = new String[3];
        columns[0] = Messages.getString("ProcessType.SUBFIELD"); //$NON-NLS-1$
        columns[1] = Messages.getString("ProcessType.DEFAULT"); //$NON-NLS-1$
        columns[2] = Messages.getString("ProcessType.MANDATORY"); //$NON-NLS-1$
    }

    public int getColumnCount() {
        return columns.length;
    }

    public int getRowCount() {
        return initialSubfields.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (initialSubfields.size() > 0) {
            USubfield usf = initialSubfields.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return usf;
                case 1:
                    return usf.getDefaultValue() == null ? "" : usf.getDefaultValue();
                case 2:
                    return isMandatory(usf);
            }
        }
        return null;
    }

    public boolean isCellEditable(int row, int col) {
        return col == 1 || col == 2;
    }

    public String getColumnName(int column) {
        return columns[column];
    }

    public Class getColumnClass(int c) {
        switch (c) {
            case 0:
                return USubfield.class;
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    public void setValueAt(Object value, int row, int col) {
        if (col == 0)
            initialSubfields.set(row, (USubfield) value);
        else if (col == 1) {
            initialSubfields.get(row).setDefaultValue((String) value);
        } else if (col == 2) {
            USubfield usf = initialSubfields.get(row);
            if (value.equals(true)) {
                if (!isMandatory(usf))
                    mandatorySubfields.add(usf);
            } else {
                removeFromMandatory(usf);
            }
        }
        fireTableCellUpdated(row, col);
    }

    public void setSubfieldsList(List<USubfield> initialList, List<USubfield> mandList) {
        if (initialList == null) {
            initialSubfields = new ArrayList<>();
        } else {
            initialSubfields = initialList;
        }
        if (mandList == null) {
            mandatorySubfields = new ArrayList<>();
        } else {
            mandatorySubfields = mandList;
        }
        fireTableDataChanged();

    }

    public List<USubfield> getInitialSubfields() {
        return initialSubfields;
    }

    public List<USubfield> getMandatorySubfields() {
        return mandatorySubfields;
    }

    public void addSubfield(USubfield sf) {
        if (!isInitial(sf))
            initialSubfields.add(sf);
        fireTableDataChanged();
    }
    /**
     * dodaje sva potpolja polja uf
     */
    public void addField(UField uf) {
        for (int i = 0; i < uf.getSubfieldCount(); i++) {
            if (!isInitial(uf.getSubfields().get(i))) {
                initialSubfields.add(uf.getSubfields().get(i));
            }
        }
        fireTableDataChanged();
    }

    public void deleteSubfield(USubfield usf) {
        if (isMandatory(usf)) {
            removeFromMandatory(usf);
        }
        if (isInitial(usf)) {
            removeFromInitial(usf);
        }
        fireTableDataChanged();
    }

    public USubfield getRow(int index) {
        return initialSubfields.get(index);
    }

    public void replaceWithPrevious(USubfield usf) {
        int index = initialSubfields.indexOf(usf);
        if (index == 0) return;
        USubfield sfPrevious = initialSubfields.get(index - 1);
        //initialSubfields.remove(usf);
        //initialSubfields.remove(sfPrevious);
        initialSubfields.set(index, sfPrevious);
        initialSubfields.set(index - 1, usf);
    }

    public void replaceWithNext(USubfield usf) {
        int index = initialSubfields.indexOf(usf);
        if (index > initialSubfields.size() - 1) return;
        USubfield sfNext = initialSubfields.get(index + 1);
        //initialSubfields.remove(usf);
        //initialSubfields.remove(sfNext);
        initialSubfields.set(index, sfNext);
        initialSubfields.set(index + 1, usf);
    }

    private boolean isMandatory(USubfield usf) {
        return mandatorySubfields.stream().anyMatch(uSubfield ->
                    (uSubfield.getName() == usf.getName() && uSubfield.getOwner() == usf.getOwner()));
    }

    private void removeFromMandatory(USubfield usf) {
        Optional<USubfield> result =
                mandatorySubfields.stream().filter(uSubfield ->
                        (uSubfield.getName() == usf.getName() && uSubfield.getOwner() == usf.getOwner())).findFirst();
        if (result.isPresent()) {
            mandatorySubfields.remove(result.get());
        }
    }

    private boolean isInitial(USubfield usf) {
        return initialSubfields.stream().anyMatch(uSubfield ->
                (uSubfield.getName() == usf.getName() && uSubfield.getOwner() == usf.getOwner()));
    }

    private void removeFromInitial(USubfield usf) {
        Optional<USubfield> result =
                initialSubfields.stream().filter(uSubfield ->
                        (uSubfield.getName() == usf.getName() && uSubfield.getOwner() == usf.getOwner())).findFirst();
        if (result.isPresent()) {
            initialSubfields.remove(result.get());
        }
    }


}
