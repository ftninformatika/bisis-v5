package com.ftninformatika.bisis.report;

import java.util.Comparator;

public class ListItemComparator implements Comparator<ListItem> {

	public enum SortOrder {ASCENDING, DESCENDING}

	private SortOrder sortOrder;
	 
	public ListItemComparator(SortOrder sortOrder) {
		    this.sortOrder = sortOrder;
    }
	@Override
	public int compare(ListItem o1, ListItem o2) {
		int compare= o1.compareTo(o2);
		if (sortOrder == SortOrder.ASCENDING) {
		      return compare;
		 } else {
		    return compare * (-1);
		 }
	}

}
