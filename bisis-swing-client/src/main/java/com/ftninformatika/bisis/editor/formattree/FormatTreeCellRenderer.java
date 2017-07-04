package com.ftninformatika.bisis.editor.formattree;

import com.ftninformatika.bisis.editor.recordtree.CurrRecord;
import com.ftninformatika.bisis.format.UField;
import com.ftninformatika.bisis.format.UFormat;
import com.ftninformatika.bisis.format.UIndicator;
import com.ftninformatika.bisis.format.USubfield;

import java.awt.Color;
import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;




public class FormatTreeCellRenderer extends DefaultTreeCellRenderer {
	
	Color mandatoryElementColor = Color.RED;
	Color addedMandatoryElementColor = Color.BLUE;
	
	private boolean secondaryMode = false;
	
	public Component getTreeCellRendererComponent(
            JTree tree,
            Object value,
            boolean sel,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus) {

		super.getTreeCellRendererComponent(
		             tree, value, sel,
		             expanded, leaf, row,
		             hasFocus);	
		StringBuffer buff = new StringBuffer();
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
			if(secondaryMode){
				if(f.isSecondary())
					buff.append("<html><b>");
			}	
			buff.append(f.getName()+"-"+f.getDescription()+" ");
			buff.append(f.isRepeatable() ? "(R)" : "(NR)");
			if(secondaryMode){
				if(f.isSecondary())
					buff.append("</b>");
			}	
			//setTextColor(f);
			text = buff.toString();						
		}		
		if (value instanceof UIndicator){
			UIndicator ind = (UIndicator) value;
			text = "ind"+ind.getIndex()+"-"+ind.getDescription();			
			//setTextColor(ind);
		}
		
		if (value instanceof USubfield){
			USubfield sf = (USubfield) value;
			text = sf.getName()+"-"+sf.getDescription();
			buff = new StringBuffer();	
			buff.append(sf.getName()+"-"+sf.getDescription()+" ");
			buff.append(sf.isRepeatable() ? "(R)" : "(NR)");			
			text = buff.toString();					
			if(/*CurrFormat.processType.getInitialSubfields().contains(sf)*/true)//TODO-hardcoded
				setForeground(Color.BLUE);				
		}		
		this.setText(text);
		return this;
}
	
	public void setSecondaryMode(boolean secondaryMode){
		this.secondaryMode = secondaryMode;	
	}
	
	
	
	private void setTextColor(Object obj){
		int mandatoryStatus = 2; //0-mandatory not added, 1-mandatory added, 2-not mandatory
		if(obj instanceof UField){
			UField uf = (UField)obj;			
			if(uf.isMandatory())
				if (CurrRecord.record.getField(uf.getName())!=null)
					mandatoryStatus = 1;					
				else mandatoryStatus = 0;
			else mandatoryStatus = 2;
		}
		if (obj instanceof USubfield){
			USubfield usf = (USubfield)obj;
			if(usf.isMandatory())
				if(CurrRecord.record.getSubfield(usf.getOwner().getName()+usf.getName())!=null)
					mandatoryStatus = 1;
				else mandatoryStatus = 0;				
		}	
		switch (mandatoryStatus){
		case 0: this.setTextNonSelectionColor(mandatoryElementColor);
				this.setTextSelectionColor(mandatoryElementColor);
				break;
		case 1: this.setTextNonSelectionColor(addedMandatoryElementColor);
				this.setTextSelectionColor(addedMandatoryElementColor);
				break;
		case 2: this.setTextNonSelectionColor(Color.BLACK);
				this.setTextSelectionColor(Color.BLACK);
				break;
		
		}
		}
	

}
