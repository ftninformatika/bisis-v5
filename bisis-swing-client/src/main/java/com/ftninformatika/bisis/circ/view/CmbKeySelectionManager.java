package com.ftninformatika.bisis.circ.view;

import com.ftninformatika.bisis.models.circ.*;
import com.ftninformatika.bisis.models.coders.Location;

import java.io.Serializable;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox.KeySelectionManager;

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
            /*if (elem instanceof Groups) {
              name = (((Groups)elem).getInstName());
            } else*/if (elem instanceof EducationLvl){
              name = (((EducationLvl)elem).getDescription());
            } else if (elem instanceof Language){
              name = (((Language)elem).getDescription());
            } else if (elem instanceof Location){
              name = (((Location)elem).getDescription());
            } else if (elem instanceof MembershipType){
              name = (((MembershipType)elem).getDescription());
            } else if (elem instanceof Organization){
              name = (((Organization)elem).getName());
            } else if (elem instanceof UserCategory){
              name = (((UserCategory)elem).getDescription());
            } else if (elem instanceof WarningTypes){
              name = (((WarningTypes)elem).getName());
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
            /*if (elem instanceof Groups) {
              name = (((Groups)elem).getInstName());
            } else*/ if (elem instanceof EducationLvl){
              name = (((EducationLvl)elem).getDescription());
            } else if (elem instanceof Language){
              name = (((Language)elem).getDescription());
            } else if (elem instanceof Location){
              name = (((Location)elem).getDescription());
            } else if (elem instanceof MembershipType){
              name = (((MembershipType)elem).getDescription());
            } else if (elem instanceof Organization){
              name = (((Organization)elem).getName());
            } else if (elem instanceof UserCategory){
              name = (((UserCategory)elem).getDescription());
            } else if (elem instanceof WarningTypes){
              name = (((WarningTypes)elem).getName());
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
