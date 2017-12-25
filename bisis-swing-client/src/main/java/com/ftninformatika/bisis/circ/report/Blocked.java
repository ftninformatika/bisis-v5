/*
 * Created on Jan 22, 2005
 *  
 */
package com.ftninformatika.bisis.circ.report;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.BisisApp;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;


public class Blocked {

	private static Document setXML(List<com.ftninformatika.bisis.circ.pojo.Report> l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		for(com.ftninformatika.bisis.circ.pojo.Report r: l){

			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(r.getProperty1());
			row.addNewColumn2().setStringValue(r.getProperty2());
			row.addNewColumn3().setStringValue(r.getProperty3());
			row.addNewColumn4().setStringValue("Blokirano od strane bibliotekara");
			row.addNewColumn5().setStringValue(r.getProperty4());
		}
	

		return report.getDomNode().getOwnerDocument();

	}

	public static JasperPrint setPrint(Object branch)
			throws IOException {
		Map<String, Object> params = new HashMap<String, Object>(1);

		String loc = "";
		if (branch instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
			params.put("nazivogr", "odeljenje: "
					+ ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription());
			loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription();
		} else {
			params.put("nazivogr", "");
		}


		JRXmlDataSource ds;
		try {
			List<com.ftninformatika.bisis.circ.pojo.Report> results = BisisApp.bisisService.getBlockedReport(loc).execute().body();
			Document dom =setXML(results);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							Blocked.class
									.getResource(
											"/jaspers/circ/blocked.jasper")
									.openStream(), params, ds);
		
			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}