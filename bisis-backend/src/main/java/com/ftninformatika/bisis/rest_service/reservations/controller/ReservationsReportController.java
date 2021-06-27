package com.ftninformatika.bisis.rest_service.reservations.controller;

import com.ftninformatika.bisis.reports.ReservationsReport;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.ReportServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author marijakovacevic
 */

@RestController
@RequestMapping("/reservations-report")
public class ReservationsReportController {

    @Autowired
    ReportServiceInterface reportService;

    @RequestMapping
    public ResponseEntity<?> getReservationsReport(@RequestHeader("Library") String library,
                                                   @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                                   @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        ReservationsReport report = reportService.getReservationsReport(start, end, library);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
