package com.ftninformatika.bisis.editor.editorutils;

import com.ftninformatika.bisis.editor.Messages;
import com.ftninformatika.bisis.format.USubfield;

import java.awt.Frame;


import java.util.List;

public class SubfieldCodeChoiceDialog extends CodeChoiceDialog {	

	public SubfieldCodeChoiceDialog(USubfield us, Frame owner) {
		super(owner, 
			Messages.getString("EDITOR_CODEDSUBFIELD"),  //$NON-NLS-1$
			us.getCoder().getItems(), 
			Messages.getString("EDITOR_LABELFIELD")+us.getOwner().getName()+"-"+us.getOwner().getDescription(),  //$NON-NLS-1$ //$NON-NLS-2$
			Messages.getString("EDITOR_LABELSUBFIELD")+us.getName()+"-"+us.getDescription()); //$NON-NLS-1$ //$NON-NLS-2$
		
	}

}
