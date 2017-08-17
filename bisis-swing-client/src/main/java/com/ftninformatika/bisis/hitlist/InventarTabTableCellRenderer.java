package com.ftninformatika.bisis.hitlist;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.border.*;

import java.awt.Component;
import java.awt.Color;
import java.awt.Rectangle;

import java.io.Serializable;



public class InventarTabTableCellRenderer extends JLabel
    implements TableCellRenderer, Serializable
{
	
	 public InventarTabTableCellRenderer(){
   		super();
			setOpaque(true);
		  setBorder(getNoFocusBorder());
   }

  
    protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1); 
    private static final Border SAFE_NO_FOCUS_BORDER = new EmptyBorder(1, 1, 1, 1);
  
    private Color unselectedForeground; 
    private Color unselectedBackground; 
    
    private Color selectedRowBackground = new Color(128,202,232);
    
   

    
   
    private static Border getNoFocusBorder() {
        if (System.getSecurityManager() != null) {
            return SAFE_NO_FOCUS_BORDER;
        } else {
            return noFocusBorder;
        }
    }

  
    public void setForeground(Color c) {
        super.setForeground(c); 
        unselectedForeground = c; 
    }
    
    
    public void setBackground(Color c) {
        super.setBackground(c); 
        unselectedBackground = c; 
    }

   
    public void updateUI() {
        super.updateUI(); 
	setForeground(null);
	setBackground(null);
    }
    
   
    public Component getTableCellRendererComponent(JTable table, Object value,
                          boolean isSelected, boolean hasFocus, int row, int column) {

        Color fg = null;
        Color bg = null;

        JTable.DropLocation dropLocation = table.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsertRow()
                && !dropLocation.isInsertColumn()
                && dropLocation.getRow() == row
                && dropLocation.getColumn() == column) {

            fg = UIManager.getColor("Table.dropCellForeground");
            bg = UIManager.getColor("Table.dropCellBackground");

            isSelected = true;
        }

        if (isSelected) {
            super.setForeground(fg == null ? table.getSelectionForeground()
                                           : fg);
            super.setBackground(bg == null ? table.getSelectionBackground()
                                           : bg);
        }
        // selektovanje tabele
        
        else if(row ==table.getSelectedRow()){
        		
        	super.setForeground(fg == null ? table.getSelectionForeground()
              : fg);
        	super.setBackground(selectedRowBackground);
        	
        } else {
            super.setForeground(unselectedForeground != null
                                    ? unselectedForeground
                                    : table.getForeground());
				    super.setBackground(unselectedBackground != null
			                                    ? unselectedBackground
			                                    : table.getBackground());
        }

	setFont(table.getFont());

	if (hasFocus) {
            Border border = null;
            if (isSelected) {
                border = UIManager.getBorder("Table.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = UIManager.getBorder("Table.focusCellHighlightBorder");
            }
            setBorder(border);

	    if (!isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = UIManager.getColor("Table.focusCellForeground");
                if (col != null) {
                    super.setForeground(col);
                }
                col = UIManager.getColor("Table.focusCellBackground");
                if (col != null) {
                    super.setBackground(col);
                }
	    }
	} else {
          setBorder(getNoFocusBorder());
	}

        setValue(value); 

	return this;
    }

    public boolean isOpaque() { 
    	Color back = getBackground();
    	Component p = getParent(); 
    	if (p != null) { 
    		p = p.getParent(); 
    	}
    	// p should now be the JTable. 
	boolean colorMatch = (back != null) && (p != null) && 
	    back.equals(p.getBackground()) && 
			p.isOpaque();
	return !colorMatch && super.isOpaque(); 
    }

    public void invalidate() {}
    
    public void validate() {}
   
    public void revalidate() {}
    
    public void repaint(long tm, int x, int y, int width, int height) {}
    
    public void repaint(Rectangle r) { }

    public void repaint() {
    }
   
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {	
	// Strings get interned...
	if (propertyName=="text"
                || propertyName == "labelFor"
                || propertyName == "displayedMnemonic"
                || ((propertyName == "font" || propertyName == "foreground")
                    && oldValue != newValue
                    && getClientProperty(javax.swing.plaf.basic.BasicHTML.propertyKey) != null)) {

            super.firePropertyChange(propertyName, oldValue, newValue);
        }
    }    
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) { }
  
    protected void setValue(Object value) {
    	setText((value == null) ? "" : value.toString());
    }
	

   

}


