package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;

import com.ftninformatika.bisis.reports.ReservationsReport;

import java.util.Date;

/**
 * @author marijakovacevic
 */
public interface ReportServiceInterface {
    ReservationsReport getReservationsReport(Date start, Date end, String library);
}
