package com.ftninformatika.bisis.rest_service.repository.mongo;


import com.ftninformatika.bisis.reports.GeneratedReport;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

/**
 * Created by dboberic on 15/10/2017.
 */
@Repository
public interface ReportsRepository extends MongoRepository<GeneratedReport, String> {
}
