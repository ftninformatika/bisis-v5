package com.ftninformatika.bisis.rest_service.reservations.controller;

import com.ftninformatika.bisis.reports.ReservationsReport;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.ReportServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author marijakovacevic
 */

@RestController
@RequestMapping("/reservations-report")
public class ReservationsReportController {

    @Autowired
    ReportServiceInterface reportService;

    @GetMapping
    public ResponseEntity<?> getReservationsReport(@RequestHeader("Library") String library) {
        ReservationsReport report = reportService.getReservationsReport(library);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
