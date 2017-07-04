
package com.ftninformatika.bisis.editor.recordtree;

import com.ftninformatika.bisis.records.Field;
import com.ftninformatika.bisis.records.Subfield;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;


public class RecordTreeCellRenderer extends DefaultTreeCellRenderer
{	 
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
		
		String text = "";
		StringBuffer buff = new StringBuffer();
    Field f = null;
		if(value instanceof Field){	
			f = (Field)value;			
			if(expanded){
				text = "<html><b>"+f.getName()+"</b>";				
			}else{				
				text = fieldToFullFormat(f);
			}
		}	
		if (value instanceof IndicatorNode){
			IndicatorNode ind = (IndicatorNode) value;
			text = "<html><b>ind"+ind.getIndex()+": </b>"+ind.getValue();			
		}
		
		if (value instanceof Subfield){      
			Subfield sf = (Subfield) value;
    /*  if(CurrFormat.isMandatorySubfield(f.getName(),String.valueOf(sf.getName())))
        this.setForeground(Color.RED);
      else
        this.setForeground(Color.BLACK);*/
			buff = new StringBuffer();
			buff.append("<html><b>["+sf.getName()+"]</b>");
			if(sf.getSecField()==null)
				buff.append(sf.getContent());
			else
				if(!expanded)					
				buff.append(fieldToFullFormat(sf.getSecField()));
			text = buff.toString();
		}	
		super.setText(text);
		return this;
}
	private String fieldToFullFormat(Field f){
		StringBuffer buff = new StringBuffer();
		buff.append("<html><b>"+f.getName()+"</b> ");
		buff.append(f.getInd1() == ' ' ? '#' : f.getInd1());
	    buff.append(f.getInd2() == ' ' ? '#' : f.getInd2());
	    buff.append(" ");
	    for(int i=0;i<f.getSubfieldCount();i++){
	    	Subfield sf = (Subfield) f.getSubfield(i);
      /*  if(CurrFormat.isMandatorySubfield(f.getName(),String.valueOf(sf.getName())))
          buff.append("<html color=\"red\"><b>["+sf.getName()+"]</b>");
        else*/
          buff.append("<html><b>["+sf.getName()+"]</b>");
          
	    	if(sf.getSecField()==null)
	    	  buff.append(sf.getContent());
			else
				buff.append(fieldToFullFormat(sf.getSecField()));
	    }
	    return buff.toString();		
	}
}
