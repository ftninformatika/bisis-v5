package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;

import com.ftninformatika.bisis.reports.ReservationsReport;

/**
 * @author marijakovacevic
 */
public interface ReportServiceInterface {
    ReservationsReport getReservationsReport(String library);
}
