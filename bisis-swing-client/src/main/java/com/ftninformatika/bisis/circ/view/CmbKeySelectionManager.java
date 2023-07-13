package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.circ.WarningType;
import com.ftninformatika.bisis.circ.pojo.*;

import javax.swing.*;
import javax.swing.JComboBox.KeySelectionManager;
import java.io.Serializable;

public class CmbKeySelectionManager implements KeySelectionManager, Serializable {
    public int selectionForKey(char aKey,ComboBoxModel aModel) {
        int i,c;
        int currentSelection = -1;
        Object selectedItem = aModel.getSelectedItem();
        String v;
        String pattern;

        if ( selectedItem != null ) {
            for ( i=0,c=aModel.getSize();i<c;i++ ) {
                if ( selectedItem == aModel.getElementAt(i) ) {
                    currentSelection  =  i;
                    break;
                }
            }
        }

        pattern = ("" + aKey).toLowerCase();
        aKey = pattern.charAt(0);

        for ( i = ++currentSelection, c = aModel.getSize() ; i < c ; i++ ) {
            Object elem = aModel.getElementAt(i);
            String name;
            if (elem instanceof CorporateMember) {
              name = (((CorporateMember)elem).getInstName());
            } else if (elem instanceof CircLocation){
              name = (((CircLocation)elem).getDescription());
            } else if (elem instanceof MembershipType){
              name = (((MembershipType)elem).getDescription());
            } else if (elem instanceof Organization){
              name = (((Organization)elem).getDescription());
            } else if (elem instanceof UserCategory){
              name = (((UserCategory)elem).getDescription());
            } else if (elem instanceof WarningType){
              name = (((WarningType)elem).getDescription());
            } else {
              name = ((elem == null) ? "" : elem.toString());
            }
            name = name.toLowerCase();
            if ( name.length() > 0 && name.charAt(0) == aKey )
              return i; 
        }

        for ( i = 0 ; i < currentSelection ; i ++ ) {
            Object elem = aModel.getElementAt(i);
            String name;
            if (elem instanceof CorporateMember) {
              name = (((CorporateMember)elem).getInstName());
            } else if (elem instanceof CircLocation){
              name = (((CircLocation)elem).getDescription());
            } else if (elem instanceof MembershipType){
              name = (((MembershipType)elem).getDescription());
            } else if (elem instanceof Organization){
              name = (((Organization)elem).getDescription());
            } else if (elem instanceof UserCategory){
              name = (((UserCategory)elem).getDescription());
            } else if (elem instanceof WarningType){
              name = (((WarningType)elem).getDescription());
            } else {
              name = ((elem == null) ? "" : elem.toString());
            }
            
            name = name.toLowerCase();
            if ( name.length() > 0 && name.charAt(0) == aKey )
              return i; 
        }
        return -1;
    }
}
