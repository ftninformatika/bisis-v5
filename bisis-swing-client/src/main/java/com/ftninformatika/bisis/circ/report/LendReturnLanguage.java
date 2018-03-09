package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Cirkulacija;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.PathDate;
import org.w3c.dom.Document;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

public class LendReturnLanguage {

	public static Document setXML(Map<String, com.ftninformatika.bisis.circ.pojo.Report> l, Date start, Date end) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Integer izPr = 0;
		Integer vrPr = 0;
		for(Map.Entry<String, com.ftninformatika.bisis.circ.pojo.Report> entry: l.entrySet()) { // idem po listi rezultata upita

			Integer iz = 0;
			Integer vr = 0;
			if (entry.getValue().getProperty1() != null)
				iz = Integer.valueOf(entry.getValue().getProperty1());
			if (entry.getValue().getProperty2() != null)
				vr = Integer.valueOf(entry.getValue().getProperty2());

			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(entry.getKey());
			row.addNewColumn2().setStringValue(String.valueOf(iz));
			row.addNewColumn3().setStringValue(String.valueOf(vr));

			izPr += iz;
			vrPr += vr;

		}
		Row row = report.addNewRow();
		row.addNewColumn1().setStringValue("UKUPNO");
		row.addNewColumn2().setStringValue(izPr + "");
		row.addNewColumn3().setStringValue(vrPr + "");

		return report.getDomNode().getOwnerDocument();
	}

	public static JasperPrint setPrint(Date start, Date end, Object location)
			throws IOException {

		Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));
		String loc = "";
		if (location instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
			params.put("nazivogr", "odeljenje: "
					+ ((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription());
			loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription();
		} else {
			params.put("nazivogr", "");
		}
		JRXmlDataSource ds;
		try {

			Map<String, com.ftninformatika.bisis.circ.pojo.Report> results = null;
			results = BisisApp.bisisService.getLendReturnLanguageReport(new PathDate(start),new PathDate(end), loc).execute().body();
			Document dom = setXML(results, start, end);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							LendReturnLanguage.class
									.getResource(
											"/cirkulacija/jaspers/izdatovracenojezik.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}

}
