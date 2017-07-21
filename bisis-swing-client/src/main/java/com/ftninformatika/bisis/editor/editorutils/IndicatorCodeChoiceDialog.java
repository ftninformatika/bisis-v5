package com.ftninformatika.bisis.editor.editorutils;

import com.ftninformatika.bisis.format.UIndicator;

import java.awt.Frame;



public class IndicatorCodeChoiceDialog extends CodeChoiceDialog {

	public IndicatorCodeChoiceDialog() {
		super();		
	}
	
	public IndicatorCodeChoiceDialog(UIndicator ind, Frame owner) {
		super(owner, "Obrada indikatora", 
				ind.getValues(), 
				"Polje: "+ind.getOwner().getName()+"-"+ind.getOwner().getDescription(),
				"ind"+ind.getIndex()+"-"+ind.getDescription()); 
		
	}

}
