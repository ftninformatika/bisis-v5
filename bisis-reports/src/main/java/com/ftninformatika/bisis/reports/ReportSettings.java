package com.ftninformatika.bisis.reports;

import java.util.ArrayList;
import java.util.List;

public class ReportSettings {

  private String reportName;
  private List<ReportParam> params;

  public ReportSettings() {
    params = new ArrayList<ReportParam>();
  }
  public ReportSettings(String reportName) {
    this();
    this.reportName = reportName;
  }
  public ReportSettings(String reportName, List<ReportParam> params) {
    this(reportName);
    this.params = params;
  }

  public List<ReportParam> getParams() {
    return params;
  }
  public void setParams(List<ReportParam> params) {
    this.params = params;
  }
  public String getReportName() {
    return reportName;
  }
  public void setReportName(String reportName) {
    this.reportName = reportName;
  }
  
  public String getParam(String name) {
    ReportParam p = findParam(name);
    if (p == null)
      return null;
    else
      return p.getValue();
  }
  
  public int getIntParam(String name) {
    ReportParam p = findParam(name);
    if (p == null)
      return Integer.MIN_VALUE;
    else
      return p.getIntValue();
  }
  
  public Period getPeriodParam(String name) {
    ReportParam p = findParam(name);
    if (p == null)
      return null;
    else
      return p.getPeriodValue();
  }
  
  public ReportType getTypeParam(String name) {
    ReportParam p = findParam(name);
    if (p == null)
      return null;
    else
      return p.getTypeValue();
  }
  
  private ReportParam findParam(String name) {
    if (name == null)
      return null;
    for (ReportParam p : params) {
      if (name.equals(p.getName()))
        return p;
    }
    return null;
  }
  
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    retVal.append('[');
    retVal.append(reportName);
    retVal.append("]\n");
    for (ReportParam p : params) {
      retVal.append("  ");
      retVal.append(p.toString());
      retVal.append('\n');
    }
    retVal.append('\n');
    return retVal.toString();
  }
  
}
