package com.ftninformatika.bisis.circ.report;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;

public class Gender {
	public static Document setXML(List<com.ftninformatika.bisis.circ.pojo.Report>  l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		for (com.ftninformatika.bisis.circ.pojo.Report r: l ) {

			String gender = r.getProperty1();

			long br = r.getProperty21();
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(gender);
			row.setColumn15(br);
		}

		return report.getDomNode().getOwnerDocument();

	}

	//ovo je za posetiocip.jrxml
	public static Document setXML(Map<String, Integer> l){
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		for(Map.Entry<String, Integer> entry: l.entrySet()){
			String gender = entry.getKey();
			long count = 0;
			if(entry.getValue() != null)
				count = entry.getValue();
			Row row = report.addNewRow();
			row.addNewColumn2().setStringValue(gender);

			row.setColumn15(count);
		}

		return report.getDomNode().getOwnerDocument();
	}

	
}
