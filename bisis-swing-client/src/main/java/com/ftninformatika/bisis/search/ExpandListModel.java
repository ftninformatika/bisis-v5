package com.ftninformatika.bisis.search;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

public class ExpandListModel extends AbstractListModel {

	public ExpandListModel(List l) {
		initList(l);
	}
	
	public int getSize() {
		return displayed.size();
	}

	public Object getElementAt(int index) {
		String expvalue = (String) displayed.get(index);
		if (expvalue == null)
			return null;
		return expvalue;
	}

	private void initList(List l) {
		displayed.clear();
		displayed.addAll(l);
	}

	List displayed=new ArrayList();
}
