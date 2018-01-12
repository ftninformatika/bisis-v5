package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.CorporateMember;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.utils.PathDate;
import org.w3c.dom.Document;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;


public class MemberByGroup {
	private static Document setXML(List<Member> l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();

		for (Member u: l){

			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(u.getUserId());
			row.addNewColumn2().setStringValue(u.getFirstName());
			row.addNewColumn3().setStringValue(u.getLastName());
		}
		return report.getDomNode().getOwnerDocument();

	}

	public static JasperPrint setPrint(Date start, Date end, Object branch,Object group) throws IOException {
				

		String loc = "";
		Map<String, Object> params = new HashMap<String, Object>(4);
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));
		if (group instanceof com.ftninformatika.bisis.circ.pojo.CorporateMember)
			params.put("group", ((com.ftninformatika.bisis.circ.pojo.CorporateMember) group).getInstName());
		if (branch instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
			params.put("nazivogr", "odeljenje: "
					+ ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription());
			loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription();
		} else {
			params.put("nazivogr", "");
		}

		JRXmlDataSource ds;
		try {
//			MemberByGroupReportCommand memberByGroup = new MemberByGroupReportCommand(start, end, branch, group);
//			memberByGroup = (MemberByGroupReportCommand)Cirkulacija.getApp().getService().executeCommand(memberByGroup);
			List<Member> l = BisisApp.bisisService.getMemberByGroupReport(new PathDate(start), new PathDate(end), ((com.ftninformatika.bisis.circ.pojo.CorporateMember) group).getInstName(), loc).execute().body();
			Document dom = setXML(l);
			ds = new JRXmlDataSource(dom, "/report/row");
			JasperPrint jp = JasperFillManager
					.fillReport(
							MemberByGroup.class
									.getResource(
											"/jaspers/circ/membersbygroup.jasper")
									.openStream(), params, ds);
			

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
