package com.ftninformatika.bisis.report;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.library_configuration.Report;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.xml.XMLUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.util.*;


public class ReportUtils {

  public static final String INV_BOOK_BGB = "com.ftninformatika.bisis.bgb.InvKnjigaMonografske";

  public static GeneratedReport loadReport(Report report) {
    try {
        List<ListItem> list = getReportList(report);
        BisisApp.getMainFrame().getReportChooserDlg().setList(list);
        BisisApp.getMainFrame().getReportChooserDlg().setVisible(true);
        if (BisisApp.getMainFrame().getReportChooserDlg().isConfirmed()) {
            ListItem selected = BisisApp.getMainFrame().getReportChooserDlg().getSelectedItem();
            return BisisApp.bisisService.getReport(selected.getReport()).execute().body();
        }else{
            return null;
        }
    }catch (Exception e) {
        e.printStackTrace();
        return null;
    }
  }



  /**
   *  Prikazuje ucitani izvestaj.
   */
  public static void showReport(GeneratedReport report, Report reportSpec) {
    HashMap<String, Object> params = new HashMap<String, Object>();
    String selectExpression = "report/item";
    try {
      if(reportSpec.getSubjasper()!=null){
    		JasperReport subreport = (JasperReport) JRLoader.loadObject(ReportUtils.class
					.getResource(reportSpec.getSubjasper()));
    		params.put("subjasper", subreport);
    	}
    	if (reportSpec.getClassName().equals(INV_BOOK_BGB))
    	    selectExpression = "*";
    	params.put("library",BisisApp.appConfig.getClientConfig().getPincodeLibrary());
        params.put("period", report.getPeriod());
        params.put("title",reportSpec.getMenuitem().replace("|", " "));
        params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());
        JRXmlDataSource dataSource = new JRXmlDataSource(XMLUtils.getDocumentFromString(report.getContent()), selectExpression);
        JasperPrint jp = JasperFillManager.fillReport(Report.class.getResource(reportSpec.getJasper()).openStream(), params, dataSource);
        BisisApp.getMainFrame().addReportFrame(report.getReportName(), jp);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Formira listu postojecih varijanti za dati izvestaj.
   */
 public static List<ListItem> getReportList(Report report) {

    List<ListItem> items = new ArrayList<ListItem>();
    try {
        List<String> generatedReports = BisisApp.bisisService.getReports(report.getType(), report.getReportName()).execute().body();
        for(String r:generatedReports) {
            ListItem item = new ListItem(r);
            items.add(item);
        }
    }catch(Exception e) {
      e.printStackTrace();
    }
     Collections.sort(items, new ListItemComparator(ListItemComparator.SortOrder.DESCENDING));
    return items;
  }

}
