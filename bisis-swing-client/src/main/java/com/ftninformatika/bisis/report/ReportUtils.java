package com.ftninformatika.bisis.report;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.library_configuration.Report;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.xml.XMLUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.File;
import java.util.*;


public class ReportUtils {

    /** Za izvestaje u kojima koristimo jasper table, prosledjujemo root xml
     *  izgenerisanog izvestaja. Ovo je lista imena klasa u kojima je to primenjeno.
     */
    static final List<String> ROOT_NODE_CLASSES = new ArrayList<>(Arrays.asList(
          "com.ftninformatika.bisis.bgb.InvKnjigaMonografske",
          "com.ftninformatika.bisis.bgb.StatistikaInventatora"));

      static final List<String> ROOT_NODE_JASPERS = new ArrayList<>(Arrays.asList(
              "/jaspers/general/InvKnjigaMonografske.jasper"));

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
            if (ROOT_NODE_CLASSES.contains(reportSpec.getClassName()) ||
                ROOT_NODE_JASPERS.contains(reportSpec.getJasper())) {
                selectExpression = "*";
                File f = new File("./tmp");
                f.mkdir();
                JRFileVirtualizer virtualizer = new JRFileVirtualizer(2,"./tmp");
                params.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
            }
            params.put("library",BisisApp.appConfig.getClientConfig().getPincodeLibrary());
            params.put("biblioteka",BisisApp.appConfig.getClientConfig().getLibraryFullName());
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
