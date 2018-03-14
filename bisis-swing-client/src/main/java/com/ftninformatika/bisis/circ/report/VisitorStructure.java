package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;


import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.PathDate;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class VisitorStructure {

	public static JasperPrint setPrint(Date start, Date end, Object location)
			throws IOException {

		try {



			Map<String, Object> params = new HashMap<String, Object>(12);
			params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());
			String loc = "";
			if (location instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
				params.put("nazivogr", "odeljenje: "
						+ ((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription());
				loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription();
			} else {
				params.put("nazivogr", "");
			}

			Map<String, Map<String, Integer>> results = null;
			results = BisisApp.bisisService.getVisitorStructureReport(new PathDate(start), new PathDate(end), loc).execute().body();
//			Document dom1 = setXMLActive(l1);
//			Document dom2 = setXMLPasiv(l2, l3,location);
			Document dom3 = setXML(results.get("clanstvo"),location);
			Document dom4 = setXML(results.get("pol") ,location);


//			JasperReport kategorija = (JasperReport) JRLoader
//					.loadObject(VisitorStructure.class
//							.getResource(
//									"/cirkulacija/jaspers/posetiocik.jasper")
//							.openStream());
//
//			JasperReport kategorijapasivni = (JasperReport) JRLoader
//					.loadObject(VisitorStructure.class
//							.getResource(
//									"/cirkulacija/jaspers/posetiocikpasiv.jasper")
//							.openStream());
//
			JasperReport uclanjenje = (JasperReport) JRLoader
					.loadObject(VisitorStructure.class
							.getResource(
									"/cirkulacija/jaspers/posetiociu.jasper")
							.openStream());

			JasperReport pol = (JasperReport) JRLoader
					.loadObject(VisitorStructure.class
							.getResource(
									"/cirkulacija/jaspers/posetiocip.jasper")
							.openStream());

			params.put("begdate", Utils.toLocaleDate(start));
			params.put("enddate", Utils.toLocaleDate(end));
			//params.put("kategorija", kategorija);
			//params.put("dom1", dom1);

			//params.put("kategorijapasivni", kategorijapasivni);
		//	params.put("dom2", dom2);

			params.put("uclanjenje", uclanjenje);
			params.put("dom3", dom3);

			params.put("pol", pol);
			params.put("dom4", dom4);

			JasperPrint jp = JasperFillManager
					.fillReport(
							Structure.class
									.getResource(
											"/cirkulacija/jaspers/strposetilaca.jasper")
									.openStream(), params,
							new JREmptyDataSource());

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

    private static Document setXMLPasiv(List l1, List l2, Object loc) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l1.iterator();
		Date returnd, signd;
		String userid;
		List poml;
		while (it.hasNext()) {
				Object[] obj = (Object[]) it.next();
				returnd = (Date) obj[2];
				userid = (String) obj[0];
		   	    Row row = report.addNewRow();
			    row.addNewColumn1().setStringValue(userid);
			    row.addNewColumn2().setStringValue((String) obj[1]);
		}
		
		Iterator it1 = l2.iterator();
     
		while (it1.hasNext()) {
			Object[] obj = (Object[]) it1.next();
			signd = (Date) obj[2];
			userid = (String) obj[0];
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(userid);
			row.addNewColumn2().setStringValue((String) obj[1]);
		}
		
		InputStream resource;
		try {
			resource = VisitorStructure.class.getResource("/com/gint/app/bisis4/client/circ/report/xmlsort.xsl").openStream();
			 //Node xmlsort = XMLTransformer.transform(report.getDomNode(), resource);
			 //return xmlsort.getOwnerDocument();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}


		return null;
	}
	 
	private static Document setXML(Map<String, Integer> l,Object loc) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		//Iterator it = l1.iterator();
		Date signd;
		String userid;
		for(Map.Entry<String, Integer> entry: l.entrySet()){
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(entry.getKey());
			row.addNewColumn2().setStringValue(String.valueOf(entry.getValue()));
		}
		/*while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
				  Row row = report.addNewRow();
			      row.addNewColumn1().setStringValue((String) obj[0]);
			      row.addNewColumn2().setStringValue((String) obj[1]);
		       
		}
		it = l2.iterator();
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();
				signd = (Date) obj[2];
				userid = (String) obj[0];
					Row row = report.addNewRow();
					row.addNewColumn1().setStringValue(userid);
					row.addNewColumn2().setStringValue((String) obj[1]);
			}*/
		InputStream resource;
//		try {
//			 resource = VisitorStructure.class.getResource("/com/gint/app/bisis4/client/circ/report/xmlsort.xsl").openStream();
//			 Node xmlsort = XMLTransformer.transform(report.getDomNode(), resource);
//		     return null;// xmlsort.getOwnerDocument();
//		} catch (IOException e) {
//		return null;
//		}
	   return report.getDomNode().getOwnerDocument();

	}

	private static Document setXMLActive(List l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Iterator it = l.iterator();
		Vector<String> userId = new Vector<String>();
		String id;
		Date lend, ret, resum;
		while (it.hasNext()) {
			Object[] obj = (Object[]) it.next();

			lend = Utils.setMaxDate((Date) obj[2]);
			ret = (Date) obj[3];
			resum = (Date) obj[4];
			id = (String) obj[0];
			if ((ret == null) && (!userId.contains(id))) {
				Row row = report.addNewRow();
				userId.add(id);
				row.addNewColumn1().setStringValue(id);
				row.addNewColumn2().setStringValue((String) obj[1]);
			} else if ((ret!=null)&&(ret.after(lend)) && (!userId.contains(id))) {
				Row row = report.addNewRow();
				userId.add(id);
				row.addNewColumn1().setStringValue(id);
				row.addNewColumn2().setStringValue((String) obj[1]);

			} else if ((resum != null) && (resum.after(lend))
					&& (!userId.contains(id))) {
				Row row = report.addNewRow();
				userId.add(id);
				row.addNewColumn1().setStringValue(id);
				row.addNewColumn2().setStringValue((String) obj[1]);
			}

		}
		try{
		 InputStream resource =  VisitorStructure.class.getResource("/com/gint/app/bisis4/client/circ/report/xmlsort.xsl").openStream();
	     //Node xmlsort = XMLTransformer.transform(report.getDomNode(), resource);
		 return null; // xmlsort.getOwnerDocument();
		}catch (Exception e) {
			return null;
		}

	}

}
