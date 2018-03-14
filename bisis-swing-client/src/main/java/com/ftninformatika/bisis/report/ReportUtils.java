package com.ftninformatika.bisis.report;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.library_configuration.Report;
import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.utils.xml.XMLUtils;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.util.*;


public class ReportUtils {


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
    try {
      if(reportSpec.getSubjasper()!=null){
    		JasperReport subreport = (JasperReport) JRLoader.loadObject(ReportUtils.class
					.getResource(reportSpec.getSubjasper()));
    		params.put("subjasper", subreport);
    	}
    	params.put("library",reportSpec.getLibrary());
        params.put("period", report.getPeriod());
        params.put("title",reportSpec.getReportTitle());
        params.put(JRParameter.REPORT_LOCALE,new Locale(BisisApp.appConfig.getClientConfig().getLocale()));
        JRXmlDataSource dataSource = new JRXmlDataSource(XMLUtils.getDocumentFromString(report.getContent()), "/report/item");
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



  /**
   * Ucitava dati izvestaj preko servleta.
   */
  /*public static String getXmlReport(String fileName) {
    String proxyHost = System.getProperty("proxyHost");
    String proxyPort = System.getProperty("proxyHost");
    System.setProperty("proxyHost", "");
    System.setProperty("proxyPort", "");
    System.setProperty("proxySet", "false");
    StringBuffer buff = new StringBuffer();
    try {
    	
      URL url = new URL(reportServletUrl + "?reportFile=" + fileName);
      URLConnection conn = url.openConnection();
      BufferedReader in = new BufferedReader(new InputStreamReader(conn
          .getInputStream(), "UTF8"));
      
      String line = "";
      while ((line = in.readLine()) != null){
        buff.append(line + "\n");
       
      }
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    System.setProperty("proxyHost", proxyHost == null ? "" : proxyHost);
    System.setProperty("proxyPort", proxyPort == null ? "" : proxyPort);
    System.setProperty("proxySet", proxyHost == null ? "false" : "true");
    
    return buff.toString();
  }

  /**
   * Ucitava dati izvestaj iz fajl sistema.
   */
 /* public static String getXmlReport(String fileName, String dir) {
    File d = new File(dir);
    File f = new File(d, fileName);
    StringBuffer buff = new StringBuffer();
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(
          new FileInputStream(f), "UTF8"));
      String line = "";
      while ((line = in.readLine()) != null)
        buff.append(line + "\n");
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return buff.toString();
  }
	public static String getPeriod(String fileName){
		if(fileName.indexOf("-")==-1){
			return "";
		}
		String period=fileName.substring(fileName.indexOf("-")+1,fileName.indexOf("."));
		return period;
		
	}
  private static String reportServletUrl;
  private static Log log = LogFactory.getLog(ReportUtils.class);
/*  static {
    INIFile iniFile = BisisApp.getINIFile();
    if (iniFile == null)
      iniFile = new INIFile(ReportUtils.class.getResource("/client-config.ini"));
    reportServletUrl = iniFile.getString("reports",
        "reportServletURL");
    if (reportServletUrl == null)
      log.fatal("Missing parameter: [reports] reportServletURL");
  }*/
}
