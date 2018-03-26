package com.ftninformatika.bisis.reports;

import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
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
    int page = 0;
    Pageable p = new PageRequest(page, 1000);
    Page<Record> records = recrep.findAll(p);

    int count = 0;
    for (Report r : reportCollection.getReports()) {
      try {
        r.init();
      } catch (Exception e) {
        log.warn("greska pri inicijalizacije izvestaja");
      }
    }
    do {
      for (Record rec : records) {
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
          if (count % 1000 == 0) {
            System.out.println("Records processed: " + count);
          }
        }
      }
      //break;
      records = recrep.findAll(records.nextPageable());
    } while (records.hasNext());

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

  //za generisanje online izvestaja
	/*public JasperPrint run(String odInvBr, String doInvBr, String reportName) {
		JasperPrint jp = null;
		try {
			List<String> listaInventarnih = new ArrayList<String>();
			int odBr = Integer.parseInt(odInvBr.substring(4));
			String odeljenje = odInvBr.substring(0, 2);
			String tippubl = odInvBr.substring(2, 4);
			int doBr = Integer.parseInt(doInvBr.substring(4));
			for (int brojac = odBr; brojac <= doBr; brojac++) {
				String brojStr = String.valueOf(brojac);
				brojStr = "00000000000".substring(0, 7 - brojStr.length())
						+ brojStr;
				listaInventarnih.add(odeljenje + tippubl + brojStr);
			}
			CachingWrapperFilter filter = new CachingWrapperFilter(
					new BisisFilter(listaInventarnih));
			Result result = BisisApp.getRecordManager().selectAll3x(
					SerializationUtils.serialize(new MatchAllDocsQuery()),
					SerializationUtils.serialize(filter), null);
			Record[] records = BisisApp.getRecordManager().getRecords(result.getRecords());
			StringBuffer buff = new StringBuffer();
			
			for (Report r : reportCollection.getReports()) {
				
				if (!r.getReportSettings().getParam("menuitem").equalsIgnoreCase(reportName)) 
					continue;
				    buff.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
				    buff.append("<report>");
					r.init();
					for (Record rec : records) {
						r.handleRecord(rec);					
					}
					r.finishOnline(buff);
					buff.append("</report>");
					JRXmlDataSource dataSource = new JRXmlDataSource(XMLUtils
							.getDocumentFromString(buff.toString()),
							"/report/item");
					Map param = new HashMap();
					if (r.getReportSettings().getParam("subjasper") != null) {
						JasperReport subreport = (JasperReport) JRLoader
								.loadObject(ReportRunner.class.getResource(
										r.getReportSettings().getParam(
												"subjasper")).openStream());
						param.put("subjasper", subreport);
					}
					jp = JasperFillManager.fillReport(ReportRunner.class
							.getResource(
									r.getReportSettings().getParam("jasper"))
							.openStream(), param, dataSource);
				}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jp;
	}

	public void runFromFile(String fileName) {
		try {
			for (Report r : reportCollection.getReports())
				r.init();
			RandomAccessFile in = new RandomAccessFile(fileName, "r");
			int count = 0;
			String line = "";
			while ((line = in.readLine()) != null) {

				count++;
				line = line.trim();
				if (line.length() == 0)
					continue;
				Record rec = null;
				try {
					rec = RecordFactory.fromUNIMARC(0, line);

				} catch (Exception ex) {
					log.warn(ex);
				}
				if (rec == null)
					continue;
				for (Report r : reportCollection.getReports()) {
					if (r.getReportSettings().getParam("type").equals("online"))
						continue;
					try {
						r.handleRecord(rec);

						if ((r.getName().startsWith("Inventarna knjiga"))
								&& (count % 2000 == 0)) {
							r.finishInv();

						}
					} catch (Exception ex) {
						log.warn(ex);
						ex.printStackTrace();
					}
					if (count % 1000 == 0) {
						System.out.println("Records processed: " + count);
					}
				}

			}
			for (Report r : reportCollection.getReports()) {
				r.finish();
			}
			System.out.println("Done reporting with " + count + " records.");
		} catch (Exception ex) {
			log.fatal(ex);
		}
	}*/

  private static ReportCollection reportCollection;
  private static RecordsRepository recrep;
  private static Logger log = Logger.getLogger(ReportRunner.class);

}
