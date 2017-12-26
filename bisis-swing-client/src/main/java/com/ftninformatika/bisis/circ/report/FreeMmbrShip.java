package com.ftninformatika.bisis.circ.report;

import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

import org.w3c.dom.Document;

public class FreeMmbrShip {
	public static Document setXML(Long num) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();
		Row row = report.addNewRow();			
		row.addNewColumn1().setStringValue(String.valueOf(num));
		return report.getDomNode().getOwnerDocument();
	}

}
