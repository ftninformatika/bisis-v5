package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.utils.PathDate;
import org.apache.xmlbeans.XmlOptions;
import org.w3c.dom.Document;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

public class LibrarianStatistic {
   //private static Map<String,Item> librarians;
   
	
    
	public static JasperPrint setPrint(Date start, Date end, Object location) throws IOException {
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
		try {
		JRXmlDataSource ds;
		List<com.ftninformatika.bisis.circ.pojo.Report> results = BisisApp.bisisService.getLibrarianStatisticReport(new PathDate(start), new PathDate(end), loc).execute().body();

			Document dom = setXML(results);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							Librarian.class
									.getResource(
											"/jaspers/circ/librarianStatistic.jasper")
									.openStream(), params, ds);

			return jp;

		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static Document setXML(List<com.ftninformatika.bisis.circ.pojo.Report> l) {
	    

		
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		//Set <String> libSet=librarians.keySet();
		Row row;

		for (com.ftninformatika.bisis.circ.pojo.Report r: l){
			row = report.addNewRow();
			row.addNewColumn1().setStringValue(r.getProperty1());
			if (r.getProperty5() == null)
				row.addNewColumn2().setStringValue("0");
			else
				row.addNewColumn2().setStringValue(r.getProperty5());

			if (r.getProperty2() == null)
				row.addNewColumn3().setStringValue("0");
			else
				row.addNewColumn3().setStringValue(r.getProperty2());

			if (r.getProperty4() == null)
				row.addNewColumn4().setStringValue("0");
			else
				row.addNewColumn4().setStringValue(r.getProperty4());

			if (r.getProperty3() == null)
				row.addNewColumn5().setStringValue("0");
			else
				row.addNewColumn5().setStringValue(r.getProperty3());
		}	      
		return report.getDomNode().getOwnerDocument();

	}

}
