package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.records.RecordPreview;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.PathDate;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;
import org.w3c.dom.Document;
public class BestBook {

	public static Document setXML(List<com.ftninformatika.bisis.circ.pojo.Report> l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();

		for (com.ftninformatika.bisis.circ.pojo.Report r: l) {
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(r.getProperty3());
			row.addNewColumn2().setStringValue(r.getProperty1());
			row.addNewColumn3().setStringValue(r.getProperty2());
		}

		return report.getDomNode().getOwnerDocument();
	}

	public static JasperPrint setPrint(Date start,Date end,Object location)throws IOException {

		Map<String, Object> params = new HashMap<String, Object>(4);
		String loc = "";
		if (location instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
			params.put("nazivogr", "odeljenje: "
					+ ((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription());
			loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription();
		} else {
			params.put("nazivogr", "");
		}
		params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());
		List<com.ftninformatika.bisis.circ.pojo.Report> l= BisisApp.bisisService.getBestBookReport(new PathDate(start), new PathDate(end), loc).execute().body();


		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate",Utils.toLocaleDate(end));

		JRXmlDataSource ds;
		try {

		 Document dom = setXML (l);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(BestBook.class
									.getResource(
											"/cirkulacija/jaspers/najcitanije.jasper").openStream(),
											params, ds);
			
			
			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}
}
