/**
 * 
 */
package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.editor.formattree.FormatTreeCellRenderer;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UFormat;
import com.ftninformatika.bisis.format.USubfield;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.TreeCellRenderer;


/**
 * @author dimicb
 *
 */
public class PubTypeTreeCellRenderer extends JLabel implements TreeCellRenderer {
	
	public PubTypeTreeCellRenderer(){
		super();
		setOpaque(true);
	}

	/* (non-Javadoc)
	 * @see javax.swing.tree.TreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int, boolean)
	 */
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean selected, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		StringBuffer buff = new StringBuffer();
		if(selected){
			setBackground(UIManager.getColor("Tree.selectionBackground"));
		}else
			setBackground(UIManager.getColor("Tree.textBackground"));
		if (expanded) {
            setIcon(new ImageIcon(FormatTreeCellRenderer.class
    				.getResource("/com/gint/app/bisis4/client/images/expanded.gif")));
            
        } else {
            setIcon(new ImageIcon(FormatTreeCellRenderer.class
    				.getResource("/com/gint/app/bisis4/client/images/collapsed.gif")));
        }	
		
		String text = "";
		
		if(value instanceof UFormat){
			text = ((UFormat)value).getName();
			setIcon(null);
		}
		if(value instanceof UField){
			UField f = (UField)value;			
			buff = new StringBuffer();
			
			buff.append(f.getName()+"-"+f.getDescription()+" ");
			buff.append(f.isRepeatable() ? "(P)" : "(NP)");			
			text = buff.toString();						
		}		
		
		if (value instanceof USubfield){
			USubfield sf = (USubfield) value;
			text = sf.getName()+"-"+sf.getDescription();
			buff = new StringBuffer();	
			buff.append(sf.getName()+"-"+sf.getDescription()+" ");
			buff.append(sf.isRepeatable() ? "(P)" : "(NP)");			
			text = buff.toString();		
		}		
		setText(text);
		return this;
	}
	
	

}
