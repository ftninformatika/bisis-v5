package com.ftninformatika.bisis.circ.report;

import java.util.HashMap;
import java.util.Map;

import com.ftninformatika.bisis.circ.view.SearchUsersTableModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;


public class SearchReport {
  
  private static Log log = LogFactory.getLog(SearchReport.class.getName());

	public static JasperPrint setPrint(SearchUsersTableModel table, String query){

		try {
  		Map<String, Object> params = new HashMap<String, Object>(1);
  		params.put("upit", query);
  		//TODO ovo ne radi zbog lokalizacije, imena kolona se ne poklapaju
  		JasperPrint jp = JasperFillManager.fillReport(SearchReport.class.getResource(
  					"/cirkulacija/jaspers/searchuser.jasper").openStream(),
            params, new JRTableModelDataSource(table));
			return jp;
		} catch (Exception e) {
			log.error(e);
			return null;
		}

	}

}
