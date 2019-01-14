package com.ftninformatika.bisis.circ.report;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.PathDate;
import com.ftninformatika.utils.string.LatCyrUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BestBookUdk {

	public static Document setXML(List<com.ftninformatika.bisis.circ.pojo.Report> l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();

		for (com.ftninformatika.bisis.circ.pojo.Report r: l) {
			Row row = report.addNewRow();
			row.addNewColumn3().setStringValue(String.valueOf(r.getProperty2()));
			row.addNewColumn1().setStringValue(r.getProperty3());
			row.addNewColumn2().setStringValue(r.getProperty1());

		}


		return report.getDomNode().getOwnerDocument();
	}

	public static JasperPrint setPrint(Date start, Date end, Object location,
			String udk) throws IOException {


		Map<String, Object> params = new HashMap<String, Object>(6);
		params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());
		params.put("reporttitle", "Најчитанија књига по УДК: " + udk);
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));
		params.put("udk", udk);
		String loc = "";
		if (location instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
			params.put("nazivogr", "одељење: "
					+ LatCyrUtils.toCyrillic(((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription()));
			loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription();
		} else {
			params.put("nazivogr", "");
		}

		List<com.ftninformatika.bisis.circ.pojo.Report> l = BisisApp.bisisService.getBestBookUdk(new PathDate(start), new PathDate(end), udk, loc).execute().body();

		JRXmlDataSource ds;
		try {

			Document dom = setXML(l);
			ds = new JRXmlDataSource(dom, "*");
			JasperPrint jp = JasperFillManager
					.fillReport(
							BestBookUdk.class
									.getResource(
											"/cirkulacija/jaspers/najcitanijetabela.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}

}
