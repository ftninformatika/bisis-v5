package com.ftninformatika.bisis.report;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.library_configuration.Report;
import com.ftninformatika.bisis.reports.GeneratedReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ReportUtils {

  /**
   * Prikazuje dijalog za izbor izvestaja, vraca niz stringova. Na prvom mestu je ucitani XML
   * izvestaj a na drugom je naziv XML fajla. Vraca null ako je korisnik odustao
   */
  public static String [] loadReport(Report report) {
	String []current=new String[2];
    List<ListItem> list = getReportList(report);
    BisisApp.getMainFrame().getReportChooserDlg().setList(list);
    BisisApp.getMainFrame().getReportChooserDlg().setVisible(true);
    if (BisisApp.getMainFrame().getReportChooserDlg().isConfirmed()) {
      ListItem selected = BisisApp.getMainFrame().getReportChooserDlg().getSelectedItem();
      current[0] = selected.getReport().getContent();
      current[1]=selected.getFileName();
      return current;
    }
    return null;
  }

  /**
   * Ucitava izvestaj iz fajla.
   */
/*  public static String [] loadReport(Report report, String dir) {
	String []currentReport=new String [2];
    List<ListItem> list = getReportList(report, dir);
    BisisApp.getMainFrame().getReportChooserDlg().setList(list);
    BisisApp.getMainFrame().getReportChooserDlg().setVisible(true);
    if (BisisApp.getMainFrame().getReportChooserDlg().isConfirmed()) {
      ListItem selected = BisisApp.getMainFrame().getReportChooserDlg()
          .getSelectedItem();
      currentReport[0] = getXmlReport(selected.getFileName(), dir);
      currentReport[1] =selected.getFileName();
      return currentReport;
    }
    return null;
  }

  /**
   *  Prikazuje ucitani izvestaj.
   */
  /*public static void showReport(String xml,String fileName, Report report) {
    HashMap<String, Object> params = new HashMap<String, Object>();
    try {
    for (ReportParam p : report.getReportSettings().getParams()){
      if(p.getName().compareToIgnoreCase("subjasper")==0){
    		JasperReport subreport = (JasperReport) JRLoader.loadObject(ReportUtils.class
					.getResource(p.getValue()).openStream());
    		params.put("subjasper", subreport);
    	}else{
         params.put(p.getName(), p.getValue());
    	}
    }

    DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance(); //dodato zbog jaxena
    JRPropertiesUtil.getInstance(context).setProperty("net.sf.jasperreports.xpath.executer.factory", "net.sf.jasperreports.engine.util.xml.JaxenXPathExecuterFactory");   
      
    params.put("period", getPeriod(fileName));
      JRXmlDataSource dataSource = new JRXmlDataSource(XMLUtils
          .getDocumentFromString(xml), "/report/item");
      JasperPrint jp = JasperFillManager.fillReport(Report.class.getResource(
          report.getJasper()).openStream(), params, dataSource);
      BisisApp.getMainFrame().addReportFrame(report.getName(), jp);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Formira listu postojecih varijanti za dati izvestaj. Poziva servlet.
   */
 public static List<ListItem> getReportList(Report report) {

    List<ListItem> items = new ArrayList<ListItem>();
    try {
        List<GeneratedReport> generatedReports = BisisApp.bisisService.getReports(report.getType(), report.getReportName()).execute().body();
        for(GeneratedReport r:generatedReports) {
            ListItem item = new ListItem(report.getReportName(), r);
            items.add(item);
        }
    }catch(Exception e) {
      e.printStackTrace();
    }
     Collections.sort(items, new ListItemComparator(ListItemComparator.SortOrder.DESCENDING));
    return items;
  }

  /**
   * Formira listu postojecih varijanti za dati izvestaj. Cita iz fajl sistema.
   */
 /* public static List<ListItem> getReportList(Report report, String dir) {
    List<ListItem> items = new ArrayList<ListItem>();
    
    try {
      File d = new File(dir);
      if (!d.isDirectory())
        return null;
      String[] files = d.list();
      for (String f : files) {
        if (!f.startsWith(report.getReportSettings().getParam("file")))
          continue;
        String suffix = "";
        String ogranak = "";
        int dashpos = f.indexOf('-');
        if (dashpos != -1)
          suffix = f.substring(dashpos + 1);
        int lbracketpos = f.indexOf('(');
        int rbracketpos = f.indexOf(')');
        if ((lbracketpos != -1)&&(rbracketpos != -1))
         ogranak = f.substring(lbracketpos, rbracketpos+1);
       
        String desc = report.getSuffixDescription(suffix);
        ListItem item = new ListItem(f, report.getName() + ogranak+" "+desc, report);
        items.add(item);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    Collections.sort(items,new ListItemComparator(SortOrder.DESCENDING));
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
