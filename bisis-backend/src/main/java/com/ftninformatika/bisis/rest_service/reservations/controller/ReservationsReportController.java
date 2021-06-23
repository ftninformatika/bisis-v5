package com.ftninformatika.bisis.rest_service.reservations.controller;

import com.ftninformatika.bisis.reports.ReservationsOnLocation;
import com.ftninformatika.bisis.reports.ReservedBookDTO;
import com.ftninformatika.bisis.rest_service.reservations.service.interfaces.ReportServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

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
        HashMap<String, List<ReservedBookDTO>> report = reportService.getReservationsReport(library);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
