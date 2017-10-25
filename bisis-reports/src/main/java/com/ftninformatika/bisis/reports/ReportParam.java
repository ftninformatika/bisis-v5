package com.ftninformatika.bisis.reports;

public class ReportParam {

  private String name;
  private String value;

  public ReportParam() {
  }
  public ReportParam(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }

  public int getIntValue() {
    return Integer.parseInt(value);
  }
  
  public Period getPeriodValue() {
    return new Period(value);
  }
  
  public ReportType getTypeValue() {
    return ReportType.getReportType(value);
  }
  
  public String toString() {
    return name + "=" + value;
  }
}
