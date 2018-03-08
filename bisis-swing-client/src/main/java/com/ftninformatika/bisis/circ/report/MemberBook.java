package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.*;


import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.utils.PathDate;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;
public class MemberBook {

	private static Document setSubXML(List<com.ftninformatika.bisis.circ.pojo.Report> l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();

		l.sort(Comparator.comparing(r -> r.getProperty10())); //da ih poredja po tipovima ucljanjenja zbog subreporta property10(mmbrType)
		for (com.ftninformatika.bisis.circ.pojo.Report r: l) {
			Row row = report.addNewRow();

			row.addNewColumn2().setStringValue(r.getProperty1());
			if(r.getProperty12()==null){
				row.addNewColumn20().setStringValue("0");
		    }else{
			row.addNewColumn20().setStringValue(String.valueOf(r.getProperty12()));
		 	}
			row.addNewColumn1().setStringValue(r.getProperty10());
		}

		return report.getDomNode().getOwnerDocument();

	}

	private static Document setXML(List<com.ftninformatika.bisis.circ.pojo.Report> l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		for (com.ftninformatika.bisis.circ.pojo.Report r: l) {
			Row row = report.addNewRow();

			row.addNewColumn1().setStringValue(r.getProperty1());
			row.addNewColumn2().setStringValue(r.getProperty2());
			row.addNewColumn3().setStringValue(r.getProperty3());
			row.addNewColumn4().setStringValue(r.getProperty4());
			if(r.getProperty5() == null){
			 row.addNewColumn6().setStringValue(null);
			}else{
			 row.addNewColumn6().setStringValue(r.getProperty5());
			}
			row.addNewColumn5().setStringValue(r.getProperty6());
			row.addNewColumn7().setStringValue(r.getProperty7());
			row.addNewColumn8().setStringValue(r.getProperty8());
			row.addNewColumn9().setStringValue(r.getProperty9());

			if(r.getProperty12() == null){
				row.addNewColumn20().setStringValue("0");
		    }else{
			row.addNewColumn20().setStringValue(String.valueOf(r.getProperty12()));
		 	}
			

		}

		return report.getDomNode().getOwnerDocument();

	}
	public static JasperPrint setPrint(Date start, Date end, Object branch)
			throws IOException {

		JRXmlDataSource ds;
		try {
			Map<String, Object> params = new HashMap<String, Object>(5);
			String loc = "";
			if (branch instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
				params.put("nazivogr", "odeljenje: "
						+ ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription());
				loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription();
			} else {
				params.put("nazivogr", "");
			}
			List<com.ftninformatika.bisis.circ.pojo.Report> results = BisisApp.bisisService.getMemberBookReport(new PathDate(start), new PathDate(end), loc).execute().body();
			Document dom = setXML(results);
			Document dom1 = setSubXML(results);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperReport subreport = (JasperReport) JRLoader
					.loadObject(MemberBook.class
							.getResource(
									"/cirkulacija/jaspers/submemberbook.jasper")
							.openStream());




			params.put("start", Utils.toLocaleDate(start));
			params.put("end", Utils.toLocaleDate(end));
			params.put("subreport", subreport);
			params.put("dom1", dom1);

			JasperPrint jp = JasperFillManager
					.fillReport(
							MemberBook.class
									.getResource(
											"/cirkulacija/jaspers/memberbook.jasper")
									.openStream(), params, ds);

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
