package com.ftninformatika.bisis.reservations.service.interfaces;

import com.ftninformatika.bisis.reports.ReservationsReport;
import com.ftninformatika.bisis.reports.ReservedBook;

import java.util.Collection;
import java.util.Date;

/**
 * @author marijakovacevic
 */
public interface ReportServiceInterface {
    ReservationsReport getReservationsFromQueue(Date start, Date end, String library);
    ReservationsReport getAssignedReservations(String library);
    ReservationsReport getPickedUpReservations(Date start, Date end, String library);
    Collection<ReservedBook> getAllByRecord(Date start, Date end, String library);
}
