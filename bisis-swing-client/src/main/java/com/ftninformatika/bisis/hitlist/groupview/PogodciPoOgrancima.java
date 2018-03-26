package com.ftninformatika.bisis.hitlist.groupview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.records.Primerak;
import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.utils.xml.XMLUtils;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRXmlDataSource;



public class PogodciPoOgrancima {
  
  public static JasperPrint execute(String[]hits, String query) {
      HashMap branchMap = new HashMap();
          try {
              for (int i = 0; i < hits.length; i++) {
                  process(BisisApp.getRecordManager().getRecord(hits[i]), branchMap);
              }
          } catch (IOException e) {
              e.printStackTrace();
          }

      StringBuffer buff = new StringBuffer();
      buff.append("<report>\n");
      List keys = new ArrayList();
      keys.addAll(branchMap.keySet());
      Collections.sort(keys);
      Iterator branchIDs = keys.iterator();
      int sum = 0;
      while (branchIDs.hasNext()) {
        String key = (String)branchIDs.next();
        Integer value = (Integer)branchMap.get(key);
        buff.append("  <row><branch>");
        buff.append(key);
    /*    buff.append(" - ");
        buff.append(HoldingsDataCoders.getValue(HoldingsDataCoders.ODELJENJE_CODER, key));*/
        buff.append("</branch><count>");
        buff.append(value);
        buff.append("</count></row>\n");
        sum += value.intValue();
      }
      buff.append("  <row><branch>Ukupno primeraka</branch><count>");
      buff.append(sum);
      buff.append("</count></row>\n");
      buff.append(" <row><branch>Ukupno zapisa</branch><count>");
      buff.append(hits.length);
      buff.append("</count></row>\n");
      buff.append("</report>\n");
      try {
	      JRXmlDataSource dataSource = new JRXmlDataSource(
	         XMLUtils.getDocumentFromString(buff.toString()), "/report/row");
	   Map param=new HashMap();
	   param.put("query", query);
        JasperPrint jp = JasperFillManager.fillReport(
            PogodciPoOgrancima.class.getResource(
              "/jaspers/groupview/PogodciPoOgrancima.jasper")
            .openStream(),param, dataSource);
          return jp;

      } catch (Exception ex) {
         return null;
      }
    }


  private static void process(Record rec, HashMap branchMap) {
  if (rec!=null){
    List <Primerak> primerci=rec.getPrimerci();
    if(primerci!=null){
    for (Primerak p:primerci){
       String invbr=p.getInvBroj();
       String status=p.getStatus();
       if (status==null)
    	  status="A";
      
      if (status.equals("9")){ // ne broji rashodovane
    	  continue;
      }
      String branchID=p.getOdeljenje();
      if((branchID==null)||(branchID.equals(""))){
    	  branchID=invbr.substring(0,2);
      }
      Integer count = (Integer)branchMap.get(branchID);
      if (count == null) {
        count = new Integer(1);
        branchMap.put(branchID, count);
      } else {
        count = new Integer(count.intValue() + 1);
        branchMap.put(branchID, count);
      }
    }
  }else{
	  System.out.println(rec.getRecordID());
  }
}
}
}