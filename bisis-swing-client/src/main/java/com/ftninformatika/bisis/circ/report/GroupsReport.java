package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.CorporateMember;
import com.ftninformatika.bisis.circ.common.Utils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;


public class GroupsReport {
	
	private static Document setXML(List<CorporateMember> l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		for(CorporateMember c: l){
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(c.getUserId());
			row.addNewColumn2().setStringValue(c.getInstName());
			if ((c.getContFirstName() != null && !(c.getContEmail()).equals("") ) || (c.getContLastName() != null && !(c.getContLastName()).equals(""))){
				row.addNewColumn3().setStringValue(c.getContFirstName() + " " + c.getContLastName());
			} else {
				row.addNewColumn3().setStringValue("");
			}
			row.addNewColumn4().setStringValue((c.getPhone() == null || c.getPhone().equals("")) ? null : c.getPhone());
			if(c.getSignDate()!=null){
			 row.addNewColumn5().setStringValue(Utils.toLocaleDate(c.getSignDate()));
			}
		
		}
		return report.getDomNode().getOwnerDocument();

	}

	public static JasperPrint setPrint( Object branch)
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
		System.out.println(loc);
		
		try {
			List<CorporateMember> corporateMembers = BisisApp.bisisService.getGroupReport(loc).execute().body();

			Document dom = setXML(corporateMembers);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							Librarian.class
									.getResource(
											"/jaspers/circ/groups.jasper")
									.openStream(), params, ds);
		

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
