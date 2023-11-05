package com.ftninformatika.bisis.circ.report;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.PathDate;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;
import org.w3c.dom.Document;
import retrofit2.Call;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookHistory {

	private static Document setXML(List<com.ftninformatika.bisis.circ.pojo.Report> l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		for (com.ftninformatika.bisis.circ.pojo.Report r : l) {
			Row row = report.addNewRow();

			row.addNewColumn1().setStringValue(r.getProperty1());
			row.addNewColumn2().setStringValue(r.getProperty2());
			row.addNewColumn3().setStringValue(r.getProperty3());
		}

		return report.getDomNode().getOwnerDocument();
	}

	private static Document setSubXML(String ctlgNo) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Row row = report.addNewRow();
		row.addNewColumn1().setStringValue(ctlgNo);
		RecordPreview record = new RecordPreview();
		Call<Record> cRecord = BisisApp.bisisService.getRecordByCtlgNo(ctlgNo);
		Record r = null;

		try {
			r = cRecord.execute().body();
			record.init(r);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (record != null) {
			row.addNewColumn2().setStringValue(record.getTitle());
			row.addNewColumn3().setStringValue(String.join(" ,", record.getAuthors()));
			row.addNewColumn4().setStringValue(record.getPublisher());
			row.addNewColumn5().setStringValue(record.getPublishingPlace());
			row.addNewColumn6().setStringValue(record.getPublishingYear());
			row.addNewColumn7().setStringValue(record.getSignature());
		}
		return report.getDomNode().getOwnerDocument();
	}

	public static JasperPrint setPrint(String ctlgNo, Date start, Date end,
			Object branch) throws IOException {
		JRXmlDataSource ds;
		try {

			List<com.ftninformatika.bisis.circ.pojo.Report> results = null;
			String loc = "";
			if (branch instanceof CircLocation)
				loc = ((CircLocation) branch).getDescription();
			results = BisisApp.bisisService.getBookHistoryReport(new PathDate(start),new PathDate(end), ctlgNo, loc).execute().body();

			Document dom = setXML(results);
			Document doc = setSubXML(ctlgNo);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperReport subreport = (JasperReport) JRLoader
					.loadObject(BookHistory.class
							.getResource(
									"/cirkulacija/jaspers/kartica_sub.jasper")
							.openStream());

			Map<String, Object> params = new HashMap<String, Object>(6);
			if (branch instanceof Location) {
				params.put("nazivogr", "odeljenje: "
						+ ((Location) branch).getDescription());
			} else {
				params.put("nazivogr", "");
			}
			params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());
			params.put("begdate", Utils.toLocaleDate(start));
			params.put("enddate", Utils.toLocaleDate(end));
			params.put("subreport", subreport);
			params.put("doc", doc);

			JasperPrint jp = JasperFillManager
					.fillReport(
							BookHistory.class
									.getResource(
											"/cirkulacija/jaspers/kartica.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
