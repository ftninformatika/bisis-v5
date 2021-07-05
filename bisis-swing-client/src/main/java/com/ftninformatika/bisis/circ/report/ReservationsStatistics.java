package com.ftninformatika.bisis.circ.report;

import com.ftninformatika.bisis.BisisApp;
import com.ftninformatika.bisis.reports.ReservationsReport;
import com.ftninformatika.utils.Messages;
import com.ftninformatika.utils.PathDate;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @author marijakovacevic
 */
public class ReservationsStatistics {
    public static JasperPrint setPrint(Date start, Date end, Object reportType) throws JRException, IOException {
        if (!BisisApp.appConfig.getClientConfig().getLibraryName().equals("bgb")) {    // todo: zakucano za bgb
            JOptionPane.showMessageDialog(null, Messages.getString("circulation.notSupported"),
                    Messages.getString("circulation.info"), JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        ReservationsReport results = null;
        InputStream inputStream = null;

        Map<String, Object> params = new HashMap<>();

        switch (reportType.toString()) {
            case "На чекању":
                inputStream = ReservationsStatistics.class.getResource("/cirkulacija/jaspers/reservationsReport.jasper").openStream();
                results = BisisApp.bisisService.getReservationsInQueue(new PathDate(start), new PathDate(end)).execute().body();
                params.put("reportTitle", "Резервације на чекању");
                break;
            case "Додељене":
                inputStream = ReservationsStatistics.class.getResource("/cirkulacija/jaspers/assignedReservations.jasper").openStream();
                results = BisisApp.bisisService.getAssignedReservations(new PathDate(start), new PathDate(end)).execute().body();
                break;
            case "Реализоване":
                inputStream = ReservationsStatistics.class.getResource("/cirkulacija/jaspers/reservationsReport.jasper").openStream();
                results = BisisApp.bisisService.getPickedUpReservations(new PathDate(start), new PathDate(end)).execute().body();
                params.put("reportTitle", "Реализоване резервације");
                break;
            default:
                break;
        }

        if (results != null && results.getReservations().size() == 0) {
            JOptionPane.showMessageDialog(null, Messages.getString("circulation.emptyReservationsReport"),
                    Messages.getString("circulation.info"), JOptionPane.INFORMATION_MESSAGE);
            return null;
        }

        JRBeanCollectionDataSource dataSource = null;
        if (results != null) {
            dataSource = new JRBeanCollectionDataSource(results.getReservations());
        }

        return JasperFillManager.fillReport(inputStream, params, dataSource);
    }
}
