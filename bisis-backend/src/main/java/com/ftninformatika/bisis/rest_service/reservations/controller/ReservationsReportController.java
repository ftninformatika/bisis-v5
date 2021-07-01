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

    @RequestMapping(path = "/in-queue")
    public ResponseEntity<?> getReservationsFromQueue(@RequestHeader("Library") String library,
                                                    @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                                    @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        ReservationsReport report = reportService.getReservationsFromQueue(start, end, library);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @RequestMapping(path = "/assigned")
    public ResponseEntity<?> getAssignedReservations(@RequestHeader("Library") String library,
                                                     @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                                     @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        ReservationsReport report = reportService.getAssignedReservations(start, end, library);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @RequestMapping(path = "/picked-up")
    public ResponseEntity<?> getPickedUpReservations(@RequestHeader("Library") String library,
                                                     @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date start,
                                                     @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date end) {
        ReservationsReport report = reportService.getPickedUpReservations(start, end, library);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
