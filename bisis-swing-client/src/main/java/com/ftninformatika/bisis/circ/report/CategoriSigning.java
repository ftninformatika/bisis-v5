package com.ftninformatika.bisis.circ.report;



import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Document;
import noNamespace.ReportDocument;
import noNamespace.ReportDocument.Report;
import noNamespace.ReportDocument.Report.Row;

public class CategoriSigning {

	public static Document setXML(List<com.ftninformatika.bisis.circ.pojo.Report>  l) {
		ReportDocument reportDoc = ReportDocument.Factory.newInstance();
		Report report = reportDoc.addNewReport();

		for (com.ftninformatika.bisis.circ.pojo.Report r: l ) {
			String uctg = r.getProperty1();
			long br = r.getProperty21();
			Row row = report.addNewRow();
			row.addNewColumn1().setStringValue(uctg);
			row.setColumn15(br);
		}

		return report.getDomNode().getOwnerDocument();

	}

}
