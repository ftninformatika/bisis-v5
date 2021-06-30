package com.ftninformatika.bisis.circ.report;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.reports.ReservationsReport;
import com.ftninformatika.utils.PathDate;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author marijakovacevic
 */
public class ReservationsStatistics {
    public static JasperPrint setPrint(Date start, Date end, Object reportType) throws JRException, IOException {
        ReservationsReport results = null;

        Map<String, Object> params = new HashMap<>();

        switch (reportType.toString()) {
            case "На чекању":
                results = BisisApp.bisisService.getReservationsInQueue(new PathDate(start), new PathDate(end)).execute().body();
                params.put("reportTitle", "Резервације на чекању");
                break;
            case "Додељене":
                results = BisisApp.bisisService.getAssignedReservations(new PathDate(start), new PathDate(end)).execute().body();
                params.put("reportTitle", "Додељене резервације");
                break;
            case "Реализоване":
                results = BisisApp.bisisService.getPickedUpReservations(new PathDate(start), new PathDate(end)).execute().body();
                params.put("reportTitle", "Реализоване резервације");
                break;
            default:
                break;
        }

        JRBeanCollectionDataSource dataSource = null;
        if (results != null) {
            dataSource = new JRBeanCollectionDataSource(results.getReservations());
        }

        InputStream inputStream = LendReturn.class.getResource("/cirkulacija/jaspers/reservationsReport.jasper").openStream();

        return JasperFillManager.fillReport(inputStream, params, dataSource);
    }
}
