package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
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


public class Visitors {

	private static Document setXML(List<com.ftninformatika.bisis.circ.pojo.Report> l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();

		for (com.ftninformatika.bisis.circ.pojo.Report r: l){
			Row row = report.addNewRow();

			row.addNewColumn1().setStringValue(r.getProperty1());
			row.addNewColumn2().setStringValue(r.getProperty2());
			row.addNewColumn3().setStringValue(r.getProperty3());
			row.addNewColumn4().setStringValue(r.getProperty4());
			if (r.getProperty6()==null){
				row.addNewColumn6().setStringValue(null);
			}else{
				row.addNewColumn6().setStringValue(r.getProperty6());
			}
			row.addNewColumn5().setStringValue(r.getProperty5());
			row.addNewColumn7().setStringValue(r.getProperty7());
			row.addNewColumn8().setStringValue((r.getProperty8()));
			row.addNewColumn9().setStringValue(r.getProperty9());
		    row.addNewColumn10().setStringValue(r.getProperty14());
		    if (r.getProperty14()==null){
			   row.addNewColumn11().setStringValue(null);
			}else{
			   row.addNewColumn11().setStringValue(r.getProperty14());
			}
			row.addNewColumn13().setStringValue(r.getProperty10());
			}

		return report.getDomNode().getOwnerDocument();

	}

	public static JasperPrint setPrint(Date date, Object branch)
			throws IOException {
		Map<String, Object> params = new HashMap<String, Object>(2);
		String loc = "";
		params.put("datum", Utils.toLocaleDate(date));
		if (branch instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
			params.put("nazivogr", "odeljenje: "
					+ ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription());
			loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription();
		} else {
			params.put("nazivogr", "");
		}
		JRXmlDataSource ds;
		try {

			List<com.ftninformatika.bisis.circ.pojo.Report> results = null;
			results = BisisApp.bisisService.getVisitorsReport(new PathDate(date), loc).execute().body();

			Document dom = setXML(results);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							Visitors.class
									.getResource(
											"/cirkulacija/jaspers/posetioci.jasper")
									.openStream(), params, ds);
			

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
