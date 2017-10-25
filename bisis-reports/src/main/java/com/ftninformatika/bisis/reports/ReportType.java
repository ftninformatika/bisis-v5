package com.ftninformatika.bisis.reports;

public enum ReportType {
  ONLINE("online"), WHOLE("whole"), YEAR("year"), HALF("half"), QUARTER("quarter"), MONTH("month"), WEEK("week");
  
  private ReportType(String desc) {
    this.description = desc;
  }
  
  public String getDescription() {
    return description;
  }
  
  @Override
  public String toString() {
    return description;
  }
  
  public static ReportType getReportType(String desc) {
    if (desc == null)
      return null;
    if (desc.equals(WHOLE.description))
      return WHOLE;
    if (desc.equals(ONLINE.description))
        return ONLINE;
    if (desc.equals(YEAR.description))
      return YEAR;
    if (desc.equals(HALF.description))
      return HALF;
    if (desc.equals(QUARTER.description))
      return QUARTER;
    if (desc.equals(MONTH.description))
      return MONTH;
    if (desc.equals(WEEK.description))
      return WEEK;
    return null;
  }
  
  private String description;
}
