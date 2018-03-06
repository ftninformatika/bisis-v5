package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.utils.PathDate;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;


public class ZbStatistic {

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
		
		com.ftninformatika.bisis.circ.pojo.Report results = BisisApp.bisisService.getZbStatisticReport(new PathDate(start), new PathDate(end), loc).execute().body();

		
		try {

			Document dom = setXML(results, location);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							Librarian.class
									.getResource(
											"/jaspers/circ/zbstatistika.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Document setXML(com.ftninformatika.bisis.circ.pojo.Report l, Object loc) {
		Object[] obj;
		String id;
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Vector<String> userId = new Vector<String>();
		Date lend, ret, resum;
		Row row = report.addNewRow();
		row.addNewColumn1().setStringValue(l.getProperty1());
		row.addNewColumn2().setStringValue(l.getProperty2());
		row.addNewColumn3().setStringValue(l.getProperty3());
	//	row.addNewColumn4().setStringValue(active.toString());
	//	row.addNewColumn5().setStringValue(count.toString());
//		Long total = Long.valueOf(l.getProperty1()) + Long.valueOf(l.getProperty2()) + Long.valueOf(l.getProperty3());
//		row.addNewColumn6().setStringValue(total.toString());
		row.addNewColumn7().setStringValue(l.getProperty4());
		row.addNewColumn8().setStringValue(l.getProperty6());
		row.addNewColumn9().setStringValue(l.getProperty5());
		row.addNewColumn10().setStringValue(( l.getProperty7()));
		row.addNewColumn11().setStringValue((l.getProperty9()));
		row.addNewColumn13().setStringValue(l.getProperty8());

		return report.getDomNode().getOwnerDocument();

	}
}
