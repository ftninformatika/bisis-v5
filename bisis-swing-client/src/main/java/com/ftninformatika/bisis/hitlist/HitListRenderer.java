package com.ftninformatika.bisis.hitlist;

import com.ftninformatika.bisis.search.Result;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.hitlist.formatters.RecordFormatter;
import com.ftninformatika.bisis.hitlist.formatters.RecordFormatterFactory;
import com.ftninformatika.bisis.records.Record;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;


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

  	if(value == null) setText(Messages.getString("HITLIST_DELETED_RECORD"));
    if (value instanceof Record) {
      Record rec = (Record)value;
      if(findRedniBroj(rec)!=-1)
      	setText(findRedniBroj(rec)+". "+formatter.toHTML(rec, "sr"));
      else
      	setText(formatter.toHTML(rec, "sr"));

      Font f = super.getFont();
      if (isSelected) {
        setForeground(/*list.getSelectionForeground()*/new Color(0x2A6191));
        //setBorder(UIManager.getBorder("Table.focusSelectedCellHighlightBorder"));
        setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        setBorder(BorderFactory.createMatteBorder(
                1, 5, 1, 1, new Color(0x40A5EC)));
      } else {
        setForeground(list.getForeground());
        setBorder(null);
        setFont(f.deriveFont(f.getStyle() &  ~Font.BOLD));
        setBackground(list.getBackground()/*new Color(0xCEDDF2)*/);
      }
    }
    return this;
  }

  // vraca redni broj pogotka u rezultatima results

  private int findRedniBroj(Record rec){
  	for(int i=0;i<results.getResultCount();i++){
  		if(rec.get_id()==results.getRecords()[i])
  			return i+1;
  	}
  	return -1;
  }
  
  private RecordFormatter formatter;
  private Result results;
}
