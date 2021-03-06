package com.ftninformatika.bisis.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.circ.common.Utils;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.PathDate;
import net.sf.jasperreports.engine.*;
import org.w3c.dom.Document;

import net.sf.jasperreports.engine.util.JRLoader;


public class FirstTimeSigned {


    public static JasperPrint setPrint(Date start, Date end, Object branch)
            throws IOException {

        try {

            Map<String, Object> params = new HashMap<String, Object>(14);
            String loc = "";
            if (branch instanceof com.ftninformatika.bisis.circ.pojo.CircLocation) {
                params.put("nazivogr", Messages.getString("DEPARTMENT_COLON")
                        + ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription());
                loc = ((com.ftninformatika.bisis.circ.pojo.CircLocation) branch).getDescription();
            } else {
                params.put("nazivogr", "");
            }

            List<com.ftninformatika.bisis.circ.pojo.Report>  l1 = BisisApp.bisisService.getCategoriaReport(new PathDate(start),new PathDate(end), loc, true).execute().body();
            List<com.ftninformatika.bisis.circ.pojo.Report>  l2 = BisisApp.bisisService.getMmbrTypeStructReport(new PathDate(start),new PathDate(end), loc, true).execute().body();
            Long numFree = BisisApp.bisisService.getFreeSigningReport(new PathDate(start),new PathDate(end), loc, true).execute().body();
            Long numUsers = BisisApp.bisisService.getUsersNumberReport(new PathDate(start),new PathDate(end), loc, true).execute().body();
            List<com.ftninformatika.bisis.circ.pojo.Report>  l5 = BisisApp.bisisService.getGenderReport(new PathDate(start),new PathDate(end), loc, true).execute().body();
            Integer totalFromYearStart = BisisApp.bisisService.getTotalSignedMembersFromStartOfYear(loc, true, new PathDate(end)).execute().body();

            Document dom1 = CategoriSigning.setXML(l1);
            Document dom2 = MmbrTypeSigning.setXML(l2);
            Document dom3 = FreeMmbrShip.setXML(numFree);
            Document dom4 = UsersNumber.setXML(numUsers);
            Document dom5 = Gender.setXML(l5);

            JasperReport brojbespl = (JasperReport) JRLoader
                    .loadObject(Structure.class
                            .getResource(
                                    "/cirkulacija/jaspers/brojbespl.jasper")
                            .openStream());
            JasperReport clanovi = (JasperReport) JRLoader
                    .loadObject(Structure.class
                            .getResource(
                                    "/cirkulacija/jaspers/clanovi.jasper")
                            .openStream());
            JasperReport placanje = (JasperReport) JRLoader
                    .loadObject(Structure.class
                            .getResource(
                                    "/cirkulacija/jaspers/placanje.jasper")
                            .openStream());
            JasperReport broj = (JasperReport) JRLoader
                    .loadObject(Structure.class
                            .getResource(
                                    "/cirkulacija/jaspers/broj.jasper")
                            .openStream());
            JasperReport pol = (JasperReport) JRLoader
                    .loadObject(Structure.class
                            .getResource(
                                    "/cirkulacija/jaspers/pol.jasper")
                            .openStream());

            params.put(JRParameter.REPORT_RESOURCE_BUNDLE, Messages.getBundle());
            params.put("title", Messages.getString("STRUKTURA_FIRST_TIME_SIGNED_TITLE"));
            params.put("begdate", Utils.toLocaleDate(start));
            params.put("enddate", Utils.toLocaleDate(end));
            params.put("clanovi", clanovi);
            params.put("dom1", dom1);
            params.put("placanje", placanje);
            params.put("dom2", dom2);
            params.put("dom3", dom3);
            params.put("brojbespl", brojbespl);
            params.put("broj", broj);
            params.put("dom4", dom4);
            params.put("pol", pol);
            params.put("dom5", dom5);
            params.put("broj_od_pocetka", totalFromYearStart);

            JasperPrint jp = JasperFillManager
                    .fillReport(
                            Structure.class
                                    .getResource(
                                            "/cirkulacija/jaspers/struktura.jasper")
                                    .openStream(), params,
                            new JREmptyDataSource());
            return jp;
        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }

    }

}
