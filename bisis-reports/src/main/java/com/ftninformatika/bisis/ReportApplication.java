package com.ftninformatika.bisis;

import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.reports.ReportCollection;
import com.ftninformatika.bisis.reports.ReportRunner;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.repository.mongo.BindingRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.RecordsRepository;
import com.ftninformatika.bisis.rest_service.repository.mongo.ReportsRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;


/**
 * Created by dboberic on 05/10/2017.
 */
@ComponentScan("com.ftninformatika")
public class ReportApplication  {

    public static void main(String[] args){

        AnnotationConfigApplicationContext  ctx = new AnnotationConfigApplicationContext();
        ctx.getEnvironment().setActiveProfiles("reporting");
        ctx.register(ReportConfig.class);
        ctx.refresh();
       List<LibraryConfiguration> libconfigs=ctx.getBean(LibraryConfigurationRepository.class).findAll();
        ReportsRepository reportRep=ctx.getBean(ReportsRepository.class);
        RecordsRepository recRep=ctx.getBean(RecordsRepository.class);
        LibraryPrefixProvider libProvider = ctx.getBean(LibraryPrefixProvider.class);
        for (LibraryConfiguration lc:libconfigs){
            libProvider.setPrefix(lc.getLibCollectionSufix());
            ReportCollection collection = new ReportCollection(lc,reportRep,ctx.getBean(BindingRepository.class));
            reportRep.deleteAll(); //obrise postojece izvestaje od prethodnog dana
            ReportRunner runner = new ReportRunner(collection,recRep);
            runner.run();
        }

    }



   /* public void run(String... args) throws Exception {
       List<LibraryConfiguration> libconfigs=ctx..findAll();
       for (LibraryConfiguration lc:libconfigs){
           libProvider.setPrefix(lc.getLibCollectionSufix());
           ReportCollection collection = new ReportCollection(lc,reportRep,binRep);
           reportRep.deleteAll(); //obrise postojece izvestaje od prethodnog dana
           ReportRunner runner = new ReportRunner(collection,recRep);
           runner.run();
          // sortReports(collection.getDestination()); //sortira izvestaje
       }
    }*/
}
