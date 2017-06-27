package com.ftninformatika.bisis.admin.coders;

import java.util.ArrayList;
import java.util.List;

public class Table {

  private String name;
  private String caption;
  private List<Column> columns;

  public Table() {
  }
  public Table(String name, String caption, List<Column> columns) {
    this.name = name;
    this.caption = caption;
    this.columns = columns;
  }
  public Table(String name, String caption, Column[] columns) {
    this.name = name;
    this.caption = caption;
    this.columns = new ArrayList<Column>();
    for (Column c : columns)
      this.columns.add(c);
  }

  public String getCaption() {
    return caption;
  }
  public void setCaption(String caption) {
    this.caption = caption;
  }
  public List<Column> getColumns() {
    return columns;
  }
  public void setColumns(List<Column> columns) {
    this.columns = columns;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
}
