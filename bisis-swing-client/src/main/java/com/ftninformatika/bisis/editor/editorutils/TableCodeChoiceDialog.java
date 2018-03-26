package com.ftninformatika.bisis.editor.editorutils;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.format.USubfield;

import java.awt.Frame;


public class TableCodeChoiceDialog extends CodeChoiceDialog {

	

		public TableCodeChoiceDialog(USubfield us, int holdingCoder, Frame owner) {
			super(owner, 
				Messages.getString("EDITOR_CODEDSUBFIELD"),  //$NON-NLS-1$
				BisisApp.appConfig.getCodersHelper().getCoder(holdingCoder),
				Messages.getString("EDITOR_LABELFIELD")+us.getOwner().getName()+"-"+us.getOwner().getDescription(),  //$NON-NLS-1$ //$NON-NLS-2$
				Messages.getString("EDITOR_LABELSUBFIELD")+us.getName()+"-"+us.getDescription()); //$NON-NLS-1$ //$NON-NLS-2$
			
		}


}
