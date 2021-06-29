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
        ReservationsReport results = BisisApp.bisisService.getReservationsStatistics(new PathDate(start), new PathDate(end)).execute().body();

        JRBeanCollectionDataSource dataSource = null;
        Map<String, Object> params = new HashMap<>();

        if (results != null) {
            switch (reportType.toString()){
                case "На чекању":
                    dataSource = new JRBeanCollectionDataSource(results.getReservationsInQueue());
                    params.put("reportTitle", "Резервације на чекању");
                    break;
                case "Додељене":
                    dataSource = new JRBeanCollectionDataSource(results.getAssignedReservations());
                    params.put("reportTitle", "Додељене резервације");
                    break;
                case "Реализоване":
                    dataSource = new JRBeanCollectionDataSource(results.getPickedUpReservations());
                    params.put("reportTitle", "Реализоване резервације");
                    break;
                default:
                    break;
            }
        }
        InputStream inputStream = LendReturn.class.getResource("/cirkulacija/jaspers/reservationsReport.jasper").openStream();


        return JasperFillManager.fillReport(inputStream, params, dataSource);
    }
}
