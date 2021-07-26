package com.ftninformatika.bisis.rest_service.controller.core;

import com.ftninformatika.bisis.records.Record;
import com.ftninformatika.bisis.rest_service.service.implementations.RescartaMetadataExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rescarta")
public class RescartaMetadataExportController {

    private final RescartaMetadataExportService rescartaMetadataExportService;

    @Autowired
    public RescartaMetadataExportController(RescartaMetadataExportService rescartaMetadataExportService) {
        this.rescartaMetadataExportService = rescartaMetadataExportService;
    }

    @RequestMapping(
            value = "/metadataExport/{RN}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> rescartaMetadataExport(@PathVariable("RN") int rn) {
        Record record = this.rescartaMetadataExportService.getRecordByRn(rn);
        return new ResponseEntity<>(record, HttpStatus.OK);
    }

}
