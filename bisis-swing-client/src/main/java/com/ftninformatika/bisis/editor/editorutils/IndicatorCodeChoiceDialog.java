package com.ftninformatika.bisis.editor.editorutils;

import com.ftninformatika.utils.Messages;
import com.ftninformatika.bisis.format.UIndicator;

import java.awt.Frame;import java.text.MessageFormat;



public class IndicatorCodeChoiceDialog extends CodeChoiceDialog {

	public IndicatorCodeChoiceDialog() {
		super();		
	}
	
	public IndicatorCodeChoiceDialog(UIndicator ind, Frame owner) {
		super(owner, Messages.getString("INDICATOR_PROCESSING"),
				ind.getValues(),
				MessageFormat.format(Messages.getString("FIELD.0"), ind.getOwner().getName(), ind.getOwner().getDescription()),
				"ind"+ind.getIndex()+"-"+ind.getDescription()); 
		
	}

}
