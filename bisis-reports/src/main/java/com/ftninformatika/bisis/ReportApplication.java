package com.ftninformatika.bisis;

import com.ftninformatika.bisis.coders.*;
import com.ftninformatika.bisis.librarian.dto.LibrarianDTO;
import com.ftninformatika.bisis.library_configuration.LibraryConfiguration;
import com.ftninformatika.bisis.reports.ReportCollection;
import com.ftninformatika.bisis.reports.ReportRunner;
import com.ftninformatika.bisis.rest_service.LibraryPrefixProvider;
import com.ftninformatika.bisis.rest_service.repository.mongo.*;
import com.ftninformatika.bisis.rest_service.repository.mongo.coders.*;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;


/**
 * Created by dboberic on 05/10/2017.
 */

@ComponentScan("com.ftninformatika")
public class ReportApplication {


  public static void main(String[] args) {

    PropertyConfigurator.configure(
        ReportApplication.class.getResourceAsStream("/log4j.properties"));
    Logger.getLogger(ReportApplication.class).info("BISIS5 Report generator starting...");


    ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)LoggerFactory.getLogger(ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME);
    root.setLevel(ch.qos.logback.classic.Level.INFO);

    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
    ctx.getEnvironment().setActiveProfiles("reporting");
    ctx.register(LibraryPrefixProvider.class);
    ctx.register(ReportConfig.class);
    ctx.refresh();


    List<LibraryConfiguration> libconfigs = ctx.getBean(LibraryConfigurationRepository.class).findAll();
    LibraryPrefixProvider libProvider = ctx.getBean(LibraryPrefixProvider.class);

    ReportsRepository reportRep = ctx.getBean(ReportsRepository.class);
    RecordsRepository recRep = ctx.getBean(RecordsRepository.class);
    for (LibraryConfiguration lc : libconfigs) {
      libProvider.setPrefix(lc.getLibraryName());
      reportRep.deleteAll();
    }
    libconfigs.sort(Comparator.comparing(LibraryConfiguration::getLibraryName));
    for (LibraryConfiguration lc : libconfigs) {

      LibraryCoders libCoders = new LibraryCoders();
      libCoders.setAccRegCoders(ctx.getBean(AccessionRegisterRepository.class).getCoders(lc.getLibraryName())
          .stream().collect(Collectors.toMap(AccessionRegister::getCoder_id, i -> i)));
      libCoders.setAcqCoders(ctx.getBean(AcquisitionRepository.class).getCoders(lc.getLibraryName())
          .stream().collect(Collectors.toMap(Acquisition::getCoder_id, i -> i)));
      libCoders.setAvailCoders(ctx.getBean(AvailabilityRepository.class).getCoders(lc.getLibraryName())
          .stream().collect(Collectors.toMap(Availability::getCoder_id, i -> i)));
      libCoders.setBinCoders(ctx.getBean(BindingRepository.class).getCoders(lc.getLibraryName())
          .stream().collect(Collectors.toMap(Binding::getCoder_id, i -> i)));
      libCoders.setFormCoders(ctx.getBean(FormatRepository.class).getCoders(lc.getLibraryName())
          .stream().collect(Collectors.toMap(Format::getCoder_id, i -> i)));
      libCoders.setIntmCoders(ctx.getBean(InternalMarkRepository.class).getCoders(lc.getLibraryName())
          .stream().collect(Collectors.toMap(InternalMark::getCoder_id, i -> i)));
      libCoders.setStCoders(ctx.getBean(ItemStatusRepository.class).getCoders(lc.getLibraryName())
          .stream().collect(Collectors.toMap(ItemStatus::getCoder_id, i -> i)));
      libCoders.setSublocCoders(ctx.getBean(SublocationRepository.class).getCoders(lc.getLibraryName())
          .stream().collect(Collectors.toMap(Sublocation::getCoder_id, i -> i)));
      libCoders.setLocCoders(ctx.getBean(LocationRepository.class).getCoders(lc.getLibraryName())
          .stream().collect(Collectors.toMap(Location::getCoder_id, i -> i)));
      libCoders.setLibrarians(ctx.getBean(LibrarianRepository.class).findAll()
          .stream().collect(Collectors.toMap(LibrarianDTO::get_id, i -> i)));


      libProvider.setPrefix(lc.getLibraryName());
      ReportCollection collection = new ReportCollection(lc, reportRep, libCoders);

      ReportRunner runner = new ReportRunner(collection, recRep);
      runner.run();
      Logger.getLogger(ReportApplication.class).info("BISIS5 Report generator finished.");
    }
  }
}
