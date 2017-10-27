package com.ftninformatika.bisis.report;


import com.ftninformatika.bisis.reports.GeneratedReport;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListItem implements Comparable{
  
  public ListItem(String fileName, GeneratedReport report) {
    this.fileName = fileName;
    this.report = report;
  }
  public ListItem() {
  }
  private String fileName;
  private GeneratedReport report;

public int compareTo(Object o) {
	return this.fileName.compareTo(((ListItem)o).fileName);
}
}
