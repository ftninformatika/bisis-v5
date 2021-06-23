package com.ftninformatika.bisis.rest_service.reservations.service.interfaces;

import com.ftninformatika.bisis.reports.ReservedBookDTO;

import java.util.HashMap;
import java.util.List;

/**
 * @author marijakovacevic
 */
public interface ReportServiceInterface {
    HashMap<String, List<ReservedBookDTO>> getReservationsReport(String library);
}
