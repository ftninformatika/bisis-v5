package com.ftninformatika.bisis.rest_service.repository.mongo.interfaces;


import com.ftninformatika.bisis.reports.GeneratedReport;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by dboberic on 15/10/2017.
 */
@Repository
public interface ReportsRepository extends MongoRepository<GeneratedReport, String> {
    @Query("{ 'reportName': ?0 , 'reportType':?1}")
    public List<GeneratedReport> getReports(String reportName,String reportType);

    @Query("{ 'fullReportName': ?0 } ")
    public GeneratedReport getReport(String reportFullName);

}
