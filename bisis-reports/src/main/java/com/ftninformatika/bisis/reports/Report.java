package com.ftninformatika.bisis.reports;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.mongo.BindingRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.ReportsRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class Report {

  public abstract void init();
  
  public abstract void finish();
  public abstract void handleRecord(Record rec);
  
  public String getName() {
    return settings.getReportName();
  }
  
  public String getJasper() {
    return settings.getJasper();
  }
  
  public void setReportSettings(com.ftninformatika.bisis.library_configuration.Report settings) {
    this.settings = settings;
  }
    public void setRepository(ReportsRepository rep) {
        this.repository = rep;
    }

  
  public com.ftninformatika.bisis.library_configuration.Report getReportSettings() {
    return settings;
  }public ReportsRepository getReportRepository() {
      return repository;
    }
  
  public ReportType getType() {
      ReportParam p = new ReportParam("type",settings.getType());
      return p.getTypeValue();
  }
  
  public String getFilenameSuffix(Date date) {
	if (getType() == ReportType.ONLINE)
	      return "";
    if (getType() == ReportType.WHOLE)
      return "";
    if (date == null)
      return "-x";
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH);
    int week = cal.get(Calendar.WEEK_OF_YEAR);
    ReportType type = getType();
    if (type == ReportType.YEAR)
      return "-" + year;
    if (type == ReportType.HALF)
      return "-" + year + ((month < 6) ? "-h1" : "-h2");
    if (type == ReportType.QUARTER) {
      String q = "q4";
      if (month < 3)
        q = "q1";
      else if (month < 6)
        q = "q2";
      else if (month < 9)
        q = "q3";
      return "-" + year + "-" + q;
    }
    if (type == ReportType.MONTH)
      return "-" + year + "-" + addZero((month+1));
    if (type == ReportType.WEEK)
      return "-" + year + "-w" + week;
    return "";
  }
  
  public String getSuffixDescription(String suffix) {
    if (suffix == null)
      return " (nepoznato)";
    if (suffix.endsWith(".xml"))
      suffix = suffix.substring(0, suffix.length()-4);
    if (suffix.equals(""))
      return " (cela baza)";
    String[] parts = suffix.split("-");
    if (parts.length == 1)
      if (parts[0].charAt(0) == 'x')
        return " (nepoznat datum)";
      else
        return parts[0];
    if (parts.length == 2) {
      switch (parts[1].charAt(0)) {
        case 'w':
          return " " + parts[0] + " nedelja " + parts[1].substring(1);
        case 'q':
          return " " + parts[0] + " kvartal " + parts[1].substring(1);
        case 'h':
          return " " + parts[0] + " polovina " + parts[1].substring(1);
        case '0':
        case '1':
        case '2':
        case '3':
        case '4':
        case '5':
        case '6':
        case '7':
        case '8':
        case '9':
          return " " + parts[0] + " " + getMonthName(parts[1]);
      }
    }
    return "";
  }
  private String getMonthName(String s) {
  
 if(s.equals("01"))
      return "januar";
 if(s.equals("02"))
      return "februar";
 if(s.equals("03"))
      return "mart";
 if(s.equals("04"))
      return "april";
 if(s.equals("05"))
      return "maj";
 if(s.equals("06"))
      return "juni";
 if(s.equals("07"))
      return "juli";
 if(s.equals("08"))
      return "avgust";
 if(s.equals("09"))
      return "septembar";
 if(s.equals("10"))
      return "oktobar";
 if(s.equals("11"))
      return "novembar";
 if(s.equals("12"))
      return "decembar";
  
  return "";
}
 /* private String getMonthName(String s) {
    int m = 0;
    try {
      m = Integer.parseInt(s);
    } catch (NumberFormatException ex) {
      ex.printStackTrace();
      return "";
    }
    switch (m) {
      case 1:
        return "januar";
      case 2:
        return "februar";
      case 3:
        return "mart";
      case 4:
        return "april";
      case 5:
        return "maj";
      case 6:
        return "juni";
      case 7:
        return "juli";
      case 8:
        return "avgust";
      case 9:
        return "septembar";
      case 10:
        return "oktobar";
      case 11:
        return "novembar";
      case 12:
        return "decembar";
    }
    return "";
  }*/
  public String addZero(int month){
	  String monthStr=Integer.toString(month);
	  if(month<10)
		  monthStr="0"+monthStr;
	  return monthStr;
  }
  protected StringBuilder  getWriter(String fileName) {
      StringBuilder  out=fileMap.get(fileName);

	try {
	  if(out==null){
	      out=new StringBuilder();
	    out.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        out.append("<report>");
        fileMap.put(fileName, out);
	  }
	} catch (Exception e) {
		e.printStackTrace();
	    return null;
	} 

    return out;
  }
  
  protected void closeFiles(String name) {
    for (StringBuilder  pw : fileMap.values()) {
      pw.append("</report>");

    }
  }
  
  protected com.ftninformatika.bisis.library_configuration.Report settings;



    protected String library;
    protected ReportsRepository repository;
    private BindingRepository binRep;
    protected Map<String, StringBuilder > fileMap = new HashMap<String, StringBuilder >();
}
