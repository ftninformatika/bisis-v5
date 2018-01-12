package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

public class CtgrUdk {

	private static Document setXML(Map<String, com.ftninformatika.bisis.circ.pojo.Report> l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();

		for (Map.Entry<String, com.ftninformatika.bisis.circ.pojo.Report> entry: l.entrySet()){
		    Row row=report.addNewRow();
		    row.addNewColumn1().setStringValue(entry.getKey());
		    row.addNewColumn2().setStringValue(entry.getValue().getProperty10());
		    row.addNewColumn3().setStringValue(entry.getValue().getProperty1());
		    row.addNewColumn4().setStringValue(entry.getValue().getProperty2());
		    row.addNewColumn5().setStringValue(entry.getValue().getProperty3());
		    row.addNewColumn6().setStringValue(entry.getValue().getProperty4());
		    row.addNewColumn7().setStringValue(entry.getValue().getProperty5());
		    row.addNewColumn8().setStringValue(entry.getValue().getProperty6());
		    row.addNewColumn9().setStringValue(entry.getValue().getProperty7());
		    row.addNewColumn10().setStringValue(entry.getValue().getProperty8());
		    row.addNewColumn11().setStringValue(entry.getValue().getProperty9());
		} 	
		return report.getDomNode().getOwnerDocument();
	}

	public static JasperPrint setPrint(Date start, Date end, Object branch)
			throws IOException {

		Map<String, Object> params = new HashMap<String, Object>(3);
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));
		String loc = "";

		if (branch instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
			params.put("nazivogr", "odeljenje: "
					+ ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription());
			loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription();
		} else {
			params.put("nazivogr", "");
		}


		Map<String, com.ftninformatika.bisis.circ.pojo.Report> l= BisisApp.bisisService.getCtgrUdkReport(new PathDate(start), new PathDate(end), loc).execute().body();
		JRXmlDataSource ds;
		try {

			Document dom = setXML(l);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							CtgrUdk.class
									.getResource(
											"/jaspers/circ/ctgrudk.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
