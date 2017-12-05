package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.Member;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.pojo.CircLocation;
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

public class MemberHistory {

    private static Document setXML(Member u) {
        ReportDocument reportDoc = ReportDocument.Factory.newInstance();
        Report report = reportDoc.addNewReport();
        Row row = report.addNewRow();
        row.addNewColumn1().setStringValue(u.getFirstName());
        row.addNewColumn2().setStringValue(u.getLastName());
        row.addNewColumn3().setStringValue(u.getAddress());
        row.addNewColumn4().setStringValue(u.getCity());
        return report.getDomNode().getOwnerDocument();

    }

    private static Document setSubXML(List<com.ftninformatika.bisis.circ.pojo.Report> l) {
        ReportDocument reportDoc = ReportDocument.Factory.newInstance();
        Report report = reportDoc.addNewReport();
        for (com.ftninformatika.bisis.circ.pojo.Report r : l) {
            Row row = report.addNewRow();
            row.addNewColumn1().setStringValue(r.getProperty1());
            row.addNewColumn2().setStringValue(r.getProperty2());
            row.addNewColumn5().setStringValue(r.getProperty5());
            row.addNewColumn4().setStringValue(r.getProperty4());
            row.addNewColumn3().setStringValue(r.getProperty3());
        }
        return report.getDomNode().getOwnerDocument();
    }


    public static JasperPrint setPrint(String userNo, Date start, Date end, CircLocation branch)
            throws IOException {
        try {
            if (start == null) {
                end = Utils.setMaxDate(end);
                start = Utils.setMinDate(end);
            } else if (end == null) {
                end = Utils.setMaxDate(start);
                start = Utils.setMinDate(start);
            } else {
                start = Utils.setMinDate(start);
                end = Utils.setMaxDate(end);
            }

            List<com.ftninformatika.bisis.circ.pojo.Report> results = null;
            Member member = null;
            try {
                member = BisisApp.bisisService.getMemberDataById(userNo).execute().body().getMember();
                results = BisisApp.bisisService.getLendingHistory(userNo, start, end, branch.getDescription()).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (member != null) {
                JRXmlDataSource ds;
                Document dom = setXML(member);
                Document doc = setSubXML(results);
                ds = new JRXmlDataSource(dom, "/report/row");
                JasperReport subreport = (JasperReport) JRLoader
                        .loadObject(MemberHistory.class
                                .getResource(
                                        "/jaspers/circ/istorija_sub.jasper")
                                .openStream());

                Map<String, Object> params = new HashMap<String, Object>(6);
                if (branch instanceof CircLocation) {
                    params.put("nazivogr", "odeljenje: " + ((CircLocation) branch).getDescription());
                } else {
                    params.put("nazivogr", "");
                }


                params.put("begdate", Utils.toLocaleDate(start));
                params.put("enddate", Utils.toLocaleDate(end));
                params.put("subreport", subreport);
                params.put("doc", doc);
                params.put("brojclana", userNo);

                JasperPrint jp = JasperFillManager
                        .fillReport(
                                MemberHistory.class
                                        .getResource(
                                                "/jaspers/circ/istorija.jasper")
                                        .openStream(), params, ds);
                return jp;
            }
            return null;
        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }

    }
}
