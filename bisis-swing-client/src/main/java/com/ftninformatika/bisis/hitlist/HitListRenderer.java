package com.ftninformatika.bisis.hitlist;

import com.ftninformatika.bisis.hitlist.formatters.RecordFormatter;
import com.ftninformatika.bisis.hitlist.formatters.RecordFormatterFactory;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.search.Result;

import java.awt.Component;

import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


public class HitListRenderer extends JEditorPane implements ListCellRenderer {


    public HitListRenderer() {
        super("text/html", "");
        setOpaque(true);
        formatter = RecordFormatterFactory.getFormatter(
                RecordFormatterFactory.FORMAT_BRIEF);
    }

    public void setFormatter(int type) {
        formatter = RecordFormatterFactory.getFormatter(type);
        if (formatter == null)
            formatter = RecordFormatterFactory.getFormatter(
                    RecordFormatterFactory.FORMAT_BRIEF);
    }

    public void setResults(Result res){
        results = res;
    }

    public Component getListCellRendererComponent(JList list, Object value,
                                                  int index, boolean isSelected, boolean cellHasFocus) {

        if(value == null) setText("Obrisan zapis");
        if (value instanceof Record) {
            Record rec = (Record)value;
            if(findRedniBroj(rec)!=-1)
                setText(findRedniBroj(rec)+". "+formatter.toHTML(rec, "sr"));
            else
                setText(formatter.toHTML(rec, "sr"));
            if (isSelected) {
                setForeground(list.getSelectionForeground());
                setBackground(list.getSelectionBackground());
            } else {
                setForeground(list.getForeground());
                setBackground(list.getBackground());
            }
        }
        return this;
    }


    // vraca redni broj pogotka u rezultatima results
    private int findRedniBroj(Record rec){
        for(int i=0; i<results.getResultCount(); i++){
            if(rec.get_id().equals(results.getRecords()[i]))
                return i+1;
        }
        return -1;
    }

    private RecordFormatter formatter;
    private Result results;
}
