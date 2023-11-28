package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.circ.WarningType;
import com.ftninformatika.bisis.circ.pojo.*;
import com.ftninformatika.bisis.coders.Location;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.Serializable;



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
		    setBackground(Color.WHITE);
		    setForeground(Color.BLACK);
		}
		else {
		    setBackground(list.getBackground());
		    setForeground(list.getForeground());
		}

		if (value instanceof CorporateMember) {
		    setText(((CorporateMember)value).getInstName());
		} else if (value instanceof CircLocation){
			setText(((CircLocation)value).getDescription());
		} else if (value instanceof MembershipType){
			setText(((MembershipType)value).getDescription());
		} else if (value instanceof Organization){
			setText(((Organization)value).getDescription());
		} else if (value instanceof UserCategory){
			setText(((UserCategory)value).getDescription());
		} else if (value instanceof WarningType){
			setText(((WarningType)value).getDescription());
		} else if (value instanceof Location){
			Location l = (Location)value;
			setText(l.getCoder_id() + " - " + l.getDescription());
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
