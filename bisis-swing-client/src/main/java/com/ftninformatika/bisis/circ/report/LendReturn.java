package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.common.Utils;
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

public class LendReturn {
	
	public static Document setXML(Map<String, com.ftninformatika.bisis.circ.pojo.Report> l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();

			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue("0");
			row.addNewColumn2().setStringValue(l.get("iz").getProperty10());
			row.addNewColumn3().setStringValue(l.get("izN").getProperty10());
			row.addNewColumn4().setStringValue(l.get("vr").getProperty10());
			row.addNewColumn5().setStringValue(l.get("vrN").getProperty10());

			row = report.addNewRow();
			row.addNewColumn1().setStringValue("1");
			row.addNewColumn2().setStringValue(l.get("iz").getProperty1());
			row.addNewColumn3().setStringValue(l.get("izN").getProperty1());
			row.addNewColumn4().setStringValue(l.get("vr").getProperty1());
			row.addNewColumn5().setStringValue(l.get("vrN").getProperty1());

			row = report.addNewRow();
			row.addNewColumn1().setStringValue("2");
			row.addNewColumn2().setStringValue(l.get("iz").getProperty2());
			row.addNewColumn3().setStringValue(l.get("izN").getProperty2());
			row.addNewColumn4().setStringValue(l.get("vr").getProperty2());
			row.addNewColumn5().setStringValue(l.get("vrN").getProperty2());

			row = report.addNewRow();
			row.addNewColumn1().setStringValue("3");
			row.addNewColumn2().setStringValue(l.get("iz").getProperty3());
			row.addNewColumn3().setStringValue(l.get("izN").getProperty3());
			row.addNewColumn4().setStringValue(l.get("vr").getProperty3());
			row.addNewColumn5().setStringValue(l.get("vrN").getProperty3());

			row = report.addNewRow();
			row.addNewColumn1().setStringValue("5");
			row.addNewColumn2().setStringValue(l.get("iz").getProperty5());
			row.addNewColumn3().setStringValue(l.get("izN").getProperty5());
			row.addNewColumn4().setStringValue(l.get("vr").getProperty5());
			row.addNewColumn5().setStringValue(l.get("vrN").getProperty5());

			row = report.addNewRow();
			row.addNewColumn1().setStringValue("6");
			row.addNewColumn2().setStringValue(l.get("iz").getProperty6());
			row.addNewColumn3().setStringValue(l.get("izN").getProperty6());
			row.addNewColumn4().setStringValue(l.get("vr").getProperty6());
			row.addNewColumn5().setStringValue(l.get("vrN").getProperty6());

			row = report.addNewRow();
			row.addNewColumn1().setStringValue("7");
			row.addNewColumn2().setStringValue(l.get("iz").getProperty7());
			row.addNewColumn3().setStringValue(l.get("izN").getProperty7());
			row.addNewColumn4().setStringValue(l.get("vr").getProperty7());
			row.addNewColumn5().setStringValue(l.get("vrN").getProperty7());

			row = report.addNewRow();
			row.addNewColumn1().setStringValue(Messages.getString("IZDATOVRACENO_DOMACE"));
			row.addNewColumn2().setStringValue(l.get("iz").getProperty8());
			row.addNewColumn3().setStringValue(l.get("izN").getProperty8());
			row.addNewColumn4().setStringValue(l.get("vr").getProperty8());
			row.addNewColumn5().setStringValue(l.get("vrN").getProperty8());

			row = report.addNewRow();
			row.addNewColumn1().setStringValue(Messages.getString("IZDATOVRACENO_STRANE"));
			row.addNewColumn2().setStringValue(l.get("iz").getProperty11());
			row.addNewColumn3().setStringValue(l.get("izN").getProperty11());
			row.addNewColumn4().setStringValue(l.get("vr").getProperty11());
			row.addNewColumn5().setStringValue(l.get("vrN").getProperty11());

			row = report.addNewRow();
			row.addNewColumn1().setStringValue("9");
			row.addNewColumn2().setStringValue(l.get("iz").getProperty9());
			row.addNewColumn3().setStringValue(l.get("izN").getProperty9());
			row.addNewColumn4().setStringValue(l.get("vr").getProperty9());
			row.addNewColumn5().setStringValue(l.get("vrN").getProperty9());

			row = report.addNewRow();
			row.addNewColumn1().setStringValue(Messages.getString("IZDATOVRACENO_UKUPNO"));
			row.addNewColumn2().setStringValue(l.get("iz").getProperty13());
			row.addNewColumn3().setStringValue(l.get("izN").getProperty13());
			row.addNewColumn4().setStringValue(l.get("vr").getProperty13());
			row.addNewColumn5().setStringValue(l.get("vrN").getProperty13());

		return report.getDomNode().getOwnerDocument();
	}

	public static JasperPrint setPrint(Date start, Date end, Object location)
			throws IOException {

		Map<String, Object> params = new HashMap<String, Object>(4);
		params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));
		String loc = "";
		if (location instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
			params.put("nazivogr", Messages.getString("DEPARTMENT_COLON")
					+ ((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription());
			loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription();
		} else {
			params.put("nazivogr", "");
		}
		JRXmlDataSource ds;
		try {

			Map<String, com.ftninformatika.bisis.circ.pojo.Report> results = BisisApp.bisisService.getLendReturnUdkReport(new PathDate(start), new PathDate(end), loc).execute().body();
			Document dom = setXML(results );
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							LendReturn.class
									.getResource(
											"/cirkulacija/jaspers/izdatovraceno.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}
}
