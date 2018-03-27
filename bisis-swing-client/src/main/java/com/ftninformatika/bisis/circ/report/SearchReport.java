package com.ftninformatika.bisis.circ.report;

import java.util.HashMap;
import java.util.Map;

import com.ftninformatika.bisis.circ.view.SearchUsersTableModel;
import com.ftninformatika.utils.Messages;
import net.sf.jasperreports.engine.JRParameter;


import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.apache.log4j.Logger;


public class SearchReport {
  
  private static Logger log = Logger.getLogger(SearchReport.class);

	public static JasperPrint setPrint(SearchUsersTableModel table, String query){

		try {
  		Map<String, Object> params = new HashMap<String, Object>(2);
  		params.put("upit", query);
  		//TODO ovo ne radi zbog lokalizacije, imena kolona se ne poklapaju
			params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());
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
