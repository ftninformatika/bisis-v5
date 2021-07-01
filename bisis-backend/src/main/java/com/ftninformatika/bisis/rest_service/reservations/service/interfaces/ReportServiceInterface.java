package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;

import com.ftninformatika.bisis.reports.ReservationsReport;

import java.util.Date;

/**
 * @author marijakovacevic
 */
public interface ReportServiceInterface {
    ReservationsReport getReservationsFromQueue(Date start, Date end, String library);
    ReservationsReport getAssignedReservations(Date start, Date end, String library);
    ReservationsReport getPickedUpReservations(Date start, Date end, String library);
}
