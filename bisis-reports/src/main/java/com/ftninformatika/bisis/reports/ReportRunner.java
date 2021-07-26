package com.ftninformatika.bisis.reports;

import com.ftninformatika.bisis.core.repositories.RecordsRepository;
import com.ftninformatika.bisis.records.Record;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class ReportRunner {

  public ReportRunner(ReportCollection reportCollection, RecordsRepository recrep) {
    this.reportCollection = reportCollection;
    this.recrep = recrep;
  }

  public void run() {

    for (Report r : reportCollection.getReports()) {
      try {
        r.init();
      } catch (Exception e) {
        log.warn("greska pri inicijalizacije izvestaja");
      }
    }

    Pageable p = new PageRequest(0, 1000);
    int count = 0;
    Page<Record> recordsPage = recrep.findAll(p);
    int pageCount = recordsPage.getTotalPages();
    for (int i = 0; i < pageCount; i++) {
      for (Record rec : recordsPage) {
        count++;
        for (Report r : reportCollection.getReports()) {
          try {
            r.handleRecord(rec);
          } catch (Exception ex) {
            ex.printStackTrace();
            log.warn("Problem with record ID: " + rec.getRecordID());
            log.warn("Report: " + r.getReportSettings().getReportName());
            log.warn(ex);
          }
          if (count % 1000 == 0)
            System.out.println("Records processed: "+ r.getReportSettings().getReportName() + " library: " + r.getLibrary() + " count: " + count);
        }
      }
      if (!recordsPage.isLast()) {
        p = recordsPage.nextPageable();
        recordsPage = recrep.findAll(p);
      }
    }

    log.info("Finishing reports");
    for (Report r : reportCollection.getReports()) {
      try {
        r.finish();
      } catch (Exception ex) {
        ex.printStackTrace();
        log.fatal(ex);
      }
    }

    log.info("Done reporting with " + count + " records.");
  }

  private static ReportCollection reportCollection;
  private static RecordsRepository recrep;
  private static Logger log = Logger.getLogger(ReportRunner.class);

}
