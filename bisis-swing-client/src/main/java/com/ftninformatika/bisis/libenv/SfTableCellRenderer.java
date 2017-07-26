/**
 * 
 */
package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.format.USubfield;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


/**
 * @author dimicb
 *
 */
public class SfTableCellRenderer extends JLabel implements TableCellRenderer {

	public SfTableCellRenderer(){
		super();
		setOpaque(true);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		if(value instanceof USubfield){
			USubfield usf = (USubfield)value;
			setText(usf.getOwner().getName()+usf.getName()+"-"+usf.getDescription());					
		}
		if (isSelected) {
      setForeground(table.getSelectionForeground());
      setBackground(table.getSelectionBackground());
    } else {
      setForeground(table.getForeground());
      setBackground(table.getBackground());
    }  
		return this;
}

}
