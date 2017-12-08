package com.ftninformatika.bisis.reports;

import com.ftninformatika.bisis.LibraryCoders;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.rest_service.repository.mongo.ReportsRepository;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ReportCollection {

 
  public ReportCollection(LibraryConfiguration lc, ReportsRepository reportsRep, LibraryCoders coders) {

      List<com.ftninformatika.bisis.library_configuration.Report> reportsConf = lc.getReports();
      reports = new ArrayList<Report>(reportsConf.size());
      for (com.ftninformatika.bisis.library_configuration.Report r : reportsConf) {
          String className = r.getClassName();
              try {
                  Report report = (Report) Class.forName(className).newInstance();
                  report.setCoders(coders);
                  report.setReportSettings(r);
                  report.setRepository(reportsRep);
                  report.setLibrary(lc.getLibraryName());
                  reports.add(report);
              } catch (Exception e) {
                  log.fatal(e);
              }
      }
  }


  public List<Report> getReports() {

      return reports;
  }
  
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    for (Report rpt : reports) {
      retVal.append(rpt.getReportSettings());
    }
    return retVal.toString();
  }


  private List<Report> reports;
  private Log log = LogFactory.getLog(ReportCollection.class);
}
