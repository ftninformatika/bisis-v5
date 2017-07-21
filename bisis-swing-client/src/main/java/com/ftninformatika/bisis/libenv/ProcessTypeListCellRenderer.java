/**
 * 
 */
package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.librarian.ProcessType;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


/**
 * @author dimicb
 *
 */
public class ProcessTypeListCellRenderer extends JLabel implements
		ListCellRenderer {	

	
	
	public ProcessTypeListCellRenderer() {
		super();
		setOpaque(true);	}

	

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (value instanceof ProcessType){
			ProcessType pt = (ProcessType)value;
			setText(pt.getName()+" ("+pt.getPubType().getName().charAt(0)+")");
			if(pt.equals(((ProcessTypeListModel)list.getModel()).getDefaultProcessType()))
				setForeground(Color.RED);
			else
				setForeground(Color.BLACK);			
			
			if (isSelected) {
	      //setForeground(list.getSelectionForeground());
	      setBackground(list.getSelectionBackground());
	    } else {
	      //setForeground(list.getForeground());
	      setBackground(list.getBackground());
	    }
		}
		return this;
	}

}
