package com.ftninformatika.bisis.rest_service.controller;

import com.ftninformatika.bisis.reports.GeneratedReport;
import com.ftninformatika.bisis.rest_service.repository.mongo.ReportsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dboberic on 27/10/2017.
 */
@RestController
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    ReportsRepository reportRep;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<String> getReports(@RequestParam("reportName")String name,@RequestParam("reportType")String type){
        List<String> reportNames=null;
        List<GeneratedReport> reports =reportRep.getReports(name,type);
        if(reports!=null){
            reportNames=new ArrayList<String>();
            for(GeneratedReport r:reports){
                reportNames.add(r.getFullReportName());
            }
        }
        return reportNames;

    }
    @RequestMapping(value = "/byFullName", method = RequestMethod.GET)
    public GeneratedReport getReport(@RequestParam("reportFullName")String name){
        return reportRep.getReport(name);
    }

    }
