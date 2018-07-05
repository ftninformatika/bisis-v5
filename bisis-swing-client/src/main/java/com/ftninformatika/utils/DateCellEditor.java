package com.ftninformatika.utils;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.Locale;
import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.TableCellEditor;

public class DateCellEditor extends AbstractCellEditor implements TableCellEditor {

    JDateChooser editor;
    int clickCountToStart = 2;

    public DateCellEditor() {
        editor = new JDateChooser();
        editor.setLocale(Locale.ENGLISH);
        editor.setDateFormatString("dd.MM.yyyy");
        editor.setFocusable(false);

        JComponent editorComponent = (JComponent)editor.getDateEditor();
        editorComponent.addAncestorListener(new AncestorListener() {

            @Override
            public void ancestorAdded(AncestorEvent event) {
                ((JComponent)editor.getDateEditor()).requestFocusInWindow();
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {}

            @Override
            public void ancestorMoved(AncestorEvent event) {}

        });
    }

    @Override
    public Object getCellEditorValue() {
        return editor.getDate();
    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            return ((MouseEvent)anEvent).getClickCount() >= clickCountToStart;
        }
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    @Override
    public void cancelCellEditing() {
        fireEditingCanceled();
    }




    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if(value instanceof java.util.Date){
            editor.setDate((java.util.Date)value);
            table.setRowHeight((int)editor.getPreferredSize().getHeight());
            //This last one is optional. It fits the row height to the JDateChooser preferred height.
        }
        return editor;
    }
}