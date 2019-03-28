package com.ftninformatika.bisis.search.net;


import com.ftninformatika.bisis.hitlist.formatters.RecordFormatter;
import com.ftninformatika.bisis.hitlist.formatters.RecordFormatterFactory;
import com.ftninformatika.bisis.records.BriefInfoModel;
import com.ftninformatika.bisis.records.Record;

import java.awt.Component;
import java.util.LinkedHashMap;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class NetHitListRenderer extends JEditorPane implements ListCellRenderer {

  public NetHitListRenderer(LinkedHashMap selectedBriefs) {
    super("text/html", "");
    setOpaque(true);
    this.selectedBriefs=selectedBriefs;
  }
  
  public void setFormatter(int type) {
    formatter = RecordFormatterFactory.getFormatter(type);
    if (formatter == null)
      formatter = RecordFormatterFactory.getFormatter(
          RecordFormatterFactory.FORMAT_BRIEF);
  }
  
  public Component getListCellRendererComponent(JList list, Object value, 
      int index, boolean isSelected, boolean cellHasFocus) {
    if (value instanceof BriefInfoModel) {
      BriefInfoModel recBrief=(BriefInfoModel)value;
      setText(recBrief.getBriefInfo().toString()+"<br/>\n&nbsp;&nbsp;<b>["+recBrief.getLibFullName()+"]</b>");
      if (isSelected) {
    	String thisLib=recBrief.getLibFullName();
		LinkedHashMap hitsToThisAddress=(LinkedHashMap)selectedBriefs.get(thisLib);
		if(hitsToThisAddress==null){
			hitsToThisAddress=new LinkedHashMap();
		}
		String recId=recBrief.getBriefInfo().get_id();
		hitsToThisAddress.put(recId,recId);
		selectedBriefs.put(thisLib,hitsToThisAddress);
        setForeground(list.getSelectionForeground());
        setBackground(list.getSelectionBackground());
      } else {
    	String thisLib=recBrief.getLibFullName();
		LinkedHashMap hitsToThisAddress=(LinkedHashMap)selectedBriefs.get(thisLib);
		if(hitsToThisAddress!=null){
			hitsToThisAddress.remove(recBrief.getBriefInfo().get_id());
			selectedBriefs.put(thisLib,hitsToThisAddress);
		}
        setForeground(list.getForeground());
        setBackground(list.getBackground());
      }
    }else if(value instanceof Record) {
    	Record rec = (Record)value;
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
  
  private RecordFormatter formatter;
  private LinkedHashMap selectedBriefs; 
}
