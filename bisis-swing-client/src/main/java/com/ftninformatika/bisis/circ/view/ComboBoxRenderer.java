package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.models.circ.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.io.Serializable;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;



public class ComboBoxRenderer extends JLabel implements ListCellRenderer, Serializable{
	
	/* 
	 * Implementation from DefaultListCellRenderer.
	 * Only getListCellRendererComponent is changed.
	 */
	protected static Border noFocusBorder;
	 
	public ComboBoxRenderer() {
		super();
	        if (noFocusBorder == null) {
	            noFocusBorder = new EmptyBorder(1, 1, 1, 1);
	        }
		setOpaque(true);
		setBorder(noFocusBorder);
    }
	
	
	public Component getListCellRendererComponent(
	        JList list,
	        Object value,
	        int index,
	        boolean isSelected,
	        boolean cellHasFocus)
	    {
	        setComponentOrientation(list.getComponentOrientation());
		if (isSelected) {
		    setBackground(list.getSelectionBackground());
		    setForeground(list.getSelectionForeground());
		}
		else {
		    setBackground(list.getBackground());
		    setForeground(list.getForeground());
		}

		if (value instanceof CorporateMember) {
		    setText(((CorporateMember)value).getInstName());
		} else if (value instanceof EducationLvl){
			setText(((EducationLvl)value).getDescription());
		} else if (value instanceof Language){
			setText(((Language)value).getDescription());
		} else if (value instanceof CircLocation){
			setText(((CircLocation)value).getDescription());
		} else if (value instanceof MembershipType){
			setText(((MembershipType)value).getDescription());
		} else if (value instanceof Organization){
			setText(((Organization)value).getName());
		} else if (value instanceof UserCategory){
			setText(((UserCategory)value).getDescription());
		} else if (value instanceof WarningTypes){
			setText(((WarningTypes)value).getName());
		} else {
			setText((value == null) ? "" : value.toString());
		}

		setEnabled(list.isEnabled());
		setFont(list.getFont());

	        Border border = null;
	        if (cellHasFocus) {
	            if (isSelected) {
	                border = UIManager.getBorder("List.focusSelectedCellHighlightBorder");
	            }
	            if (border == null) {
	                border = UIManager.getBorder("List.focusCellHighlightBorder");
	            }
	        } else {
	            border = noFocusBorder;
	        }
		setBorder(border);

		return this;
    }
	
	
	public boolean isOpaque() { 
		Color back = getBackground();
		Component p = getParent(); 
		if (p != null) { 
		    p = p.getParent(); 
		}
		// p should now be the JList. 
		boolean colorMatch = (back != null) && (p != null) && 
		    back.equals(p.getBackground()) && 
				p.isOpaque();
		return !colorMatch && super.isOpaque(); 
    }
	
	public void validate() {}
	
	public void invalidate() {}
	
	public void repaint() {}
	
	public void revalidate() {}
	
	public void repaint(long tm, int x, int y, int width, int height) {}
	
	public void repaint(Rectangle r) {}
	
	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		// Strings get interned...
		if (propertyName=="text")
		    super.firePropertyChange(propertyName, oldValue, newValue);
    }
	
	public void firePropertyChange(String propertyName, byte oldValue, byte newValue) {}
	
	public void firePropertyChange(String propertyName, char oldValue, char newValue) {}
	
	public void firePropertyChange(String propertyName, short oldValue, short newValue) {}
	
	public void firePropertyChange(String propertyName, int oldValue, int newValue) {}
	
	public void firePropertyChange(String propertyName, long oldValue, long newValue) {}
	
	public void firePropertyChange(String propertyName, float oldValue, float newValue) {}
	
	public void firePropertyChange(String propertyName, double oldValue, double newValue) {}
	
	public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue) {}
	
	public static class UIResource extends DefaultListCellRenderer
    implements javax.swing.plaf.UIResource
    {
    }
	
}
