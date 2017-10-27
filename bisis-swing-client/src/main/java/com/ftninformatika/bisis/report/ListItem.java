package com.ftninformatika.bisis.report;


import com.ftninformatika.bisis.reports.GeneratedReport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListItem implements Comparable{

    private String report;
  
  public ListItem(String report) {
    this.report = report;
  }
  public ListItem() {
  }

public int compareTo(Object o) {
	return this.report.compareTo(((ListItem)o).getReport());
}

}
