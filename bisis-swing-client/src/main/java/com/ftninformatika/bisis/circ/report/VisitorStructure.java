package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.PathDate;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.w3c.dom.Document;

public class VisitorStructure {

	public static JasperPrint setPrint(Date start, Date end, Object location)
			throws IOException {

		try {

			Map<String, Object> params = new HashMap<String, Object>(13);
			params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());
			String loc = "";
			if (location instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
				params.put("nazivogr", "odeljenje: "
						+ ((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription());
				loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) location).getDescription();
			} else {
				params.put("nazivogr", "");
			}

			Map<String, Map<String, Integer>> results = BisisApp.bisisService
														.getVisitorStructureReport(new PathDate(start), new PathDate(end), loc).execute().body();

			Document dom1 = MmbrTypeSigning.setXML(results.get("kategorija"));
			Document dom3 = Gender.setXML(results.get("pol"));
			Document dom4 = CategoriSigning.setXML(results.get("vrstaClanstva"));


			JasperReport kategorija = (JasperReport) JRLoader
					.loadObject(VisitorStructure.class
							.getResource(
									"/cirkulacija/jaspers/posetiocik.jasper")
							.openStream());
			JasperReport uclanjenje = (JasperReport) JRLoader
					.loadObject(VisitorStructure.class
							.getResource(
									"/cirkulacija/jaspers/posetiociu.jasper")
							.openStream());

			JasperReport pol = (JasperReport) JRLoader
					.loadObject(VisitorStructure.class
							.getResource(
									"/cirkulacija/jaspers/posetiocip.jasper")
							.openStream());


			params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());
			params.put("begdate", Utils.toLocaleDate(start));
			params.put("enddate", Utils.toLocaleDate(end));
			params.put("kategorija", kategorija);
			params.put("dom1", dom1);
			params.put("pol", pol);
			params.put("dom3", dom3);
			params.put("uclanjenje", uclanjenje);
			params.put("dom4", dom4);

			JasperPrint jp = JasperFillManager
					.fillReport(
							Structure.class
									.getResource(
											"/cirkulacija/jaspers/strposetilaca.jasper")
									.openStream(), params,
							new JREmptyDataSource());

			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}
}
