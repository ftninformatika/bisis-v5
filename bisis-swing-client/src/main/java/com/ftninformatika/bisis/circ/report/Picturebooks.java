package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.bisis.circ.pojo.Report;
import com.ftninformatika.utils.PathDate;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;


public class Picturebooks {

	public static JasperPrint setPrint(Date start, Date end)
			throws IOException {
		Map<String, Object> params = new HashMap<String, Object>(5);

		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));

		Report pictureBooks = BisisApp.bisisService.getPicturebooksReport(new PathDate(start), new PathDate(end)).execute().body();
		params.put("users", (pictureBooks.getProperty1()));
		params.put("lend", (pictureBooks.getProperty2()));
		params.put("return", (pictureBooks.getProperty3()));
		
		try {

			JasperPrint jp = JasperFillManager.fillReport(Picturebooks.class.getResource(
											"/jaspers/circ/picturebooks.jasper")
									.openStream(), params, new JREmptyDataSource());
			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}
}
