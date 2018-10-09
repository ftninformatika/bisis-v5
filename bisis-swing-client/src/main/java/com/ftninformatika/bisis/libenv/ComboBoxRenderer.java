package com.ftninformatika.bisis.libenv;

import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.coders.Location;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class ComboBoxRenderer extends JLabel implements ListCellRenderer, Serializable {

    public ComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        if (value instanceof Location) {
            setText(((Location) value).getDescription());
        } else if (value instanceof CircLocation) {
            setText(((CircLocation) value).getDescription());
        } else {
            setText((value == null) ? "" : value.toString());
        }
        return this;
    }
}
