package com.ftninformatika.bisis.editor.inventar;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.editor.editorutils.CodeChoiceDialog;

import java.util.List;


public class InventarCodeChoiceDialog extends CodeChoiceDialog {

	public InventarCodeChoiceDialog(String title, List cList) {
		super(BisisApp.getMainFrame(), title, cList, "", "");
	}

}
