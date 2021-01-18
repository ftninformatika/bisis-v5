package com.ftninformatika.bisisris.controllers;

import com.ftninformatika.bisis.circ.CircLocation;
import com.ftninformatika.bisis.coders.Location;
import com.ftninformatika.bisis.coders.Sublocation;
import com.ftninformatika.bisisauthentication.LibraryPrefixProvider;
import com.ftninformatika.bisisris.dto.LibrarianReport;
import com.ftninformatika.bisisris.dto.LocationReport;
import com.ftninformatika.bisisris.dto.MapReduceValueObjectLibrarian;
import com.ftninformatika.bisisris.dto.MapReduceValueObjectLocation;
import com.ftninformatika.bisis.librarian.db.LibrarianDB;
import com.ftninformatika.bisisauthentication.repositories.LibrarianRepository;
import com.ftninformatika.bisisris.repositories.CircLocationRepository;
import com.ftninformatika.bisisris.repositories.LocationRepository;
import com.ftninformatika.bisisris.repositories.SubLocationRepository;
import com.ftninformatika.bisisris.repositories.TaskRepository;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    TaskRepository tr;
    @Qualifier("librarianAuthenticationRepository")
    @Autowired
    LibrarianRepository lr;
    @Autowired
    LibraryPrefixProvider lpp;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CircLocationRepository circLocationRepository;

    @Autowired
    MongoTemplate mt;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
    ex.printStackTrace();
    }

    @PostMapping("librarians")
    public List<LibrarianDB> getAllLibrarianWithRoles(@RequestBody String[] roles){
        return lr.findByAuthoritiesIn(roles);
    }
    @GetMapping("tasksByLibrarian/{librarian}")
    public void tasksByLibrarianToPDF(HttpServletResponse response, @PathVariable("librarian") String librarian, @RequestParam(name = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @RequestParam(name = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws Exception {
        String libPrefix = lpp.getLibPrefix();
        try {
            String mapF = "function(){\n" +
                    " if (this.method==='Телефоном'){\n" +
                    " emit(this.serviceType,{\"inLibrary\":0,\"byPhone\":this.quantity, \"online\":0});\n" +
                    " } else if (this.method==='У библиотеци'){\n" +
                    "emit(this.serviceType,{\"inLibrary\":this.quantity,\"byPhone\":0, \"online\":0});\n" +
                    "}else{\n" +
                    " emit(this.serviceType,{\"inLibrary\":0,\"byPhone\":0, \"online\":this.quantity});\n" +
                    "}\n" +
                    "}";

            String reduceF = "function(key, values) {\n" +
                    "var res = {\"inLibrary\":0,\"byPhone\":0, \"online\":0}\n" +
                    "values.forEach(v =>{\n" +
                    "res.inLibrary = res.inLibrary + v.inLibrary;\n" +
                    "res.byPhone = res.byPhone + v.byPhone;\n" +
                    "res.online = res.online + v.online;\n" +
                    "});\n" +
                    "return res;\n" +
                    "};";
            Date queryDate = new Date();
            queryDate.setTime(toDate.getTime()+86400000);
            Query query = new Query(Criteria.where("dateOfService").gte(fromDate).lt(queryDate).andOperator(Criteria.where("librarian").is(librarian)));

            MapReduceResults<MapReduceValueObjectLibrarian> res = mt.mapReduce(query, libPrefix + "_task", mapF, reduceF, MapReduceValueObjectLibrarian.class);
            List<LibrarianReport> finalItemList = new ArrayList<>();
            for (MapReduceValueObjectLibrarian v : res) {
                LibrarianReport r = new LibrarianReport();
                r.setServiceType(v.getId());
                r.setByPhone(v.getValue().getByPhone());
                r.setInLibrary(v.getValue().getInLibrary());
                r.setOnline(v.getValue().getOnline());
                finalItemList.add(r);
            }
            if (finalItemList.isEmpty()) {
                response.setStatus(404);
            } else {
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(finalItemList);
                InputStream inputStream = this.getClass().getResourceAsStream("/jaspers/tasksByLibrarian.jasper");
                Map<String, Object> params = new HashMap<String, Object>();
                Optional<LibrarianDB> librarianObj = lr.findByUsername(librarian);
                if (librarianObj.isPresent()) {
                    params.put("librarian", librarianObj.get().getIme() + " " + librarianObj.get().getPrezime());
                }
                params.put("fromDate", fromDate);
                params.put("toDate", toDate);
                JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
                inputStream.close();
                response.setContentType("application/octet-stream");
                String attachment = "attachment; filename=tasksSheet.pdf";
                response.setHeader("Content-Disposition", attachment);
                OutputStream out = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            }
        } catch (Exception e) {
            response.setStatus(500);
        }
    }

    @GetMapping("tasksByLocation/")
    public void tasksByLocationToPDF(HttpServletResponse response, @RequestParam(name = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @RequestParam(name = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws Exception {
        String libPrefix = lpp.getLibPrefix();

        try {
            String mapF = "function(){\n" +
                    " if (this.method==='Телефоном'){\n" +
                    " emit({\"location\":this.location,\"serviceTypeCode\":this.serviceTypeCode},{\"inLibrary\":0,\"byPhone\":this.quantity, \"online\":0});\n" +
                    " } else if (this.method==='У библиотеци'){\n" +
                    "emit({\"location\":this.location,\"serviceTypeCode\":this.serviceTypeCode},{\"inLibrary\":this.quantity,\"byPhone\":0, \"online\":0});\n" +
                    "}else{\n" +
                    " emit({\"location\":this.location,\"serviceTypeCode\":this.serviceTypeCode},{\"inLibrary\":0,\"byPhone\":0, \"online\":this.quantity});\n" +
                    "}\n" +
                    "}";

            String reduceF = "function(key, values) {\n" +
                    "var res = {\"inLibrary\":0,\"byPhone\":0, \"online\":0}\n" +
                    "values.forEach(v =>{\n" +
                    "res.inLibrary = res.inLibrary + v.inLibrary;\n" +
                    "res.byPhone = res.byPhone + v.byPhone;\n" +
                    "res.online = res.online + v.online;\n" +
                    "});\n" +
                    "return res;\n" +
                    "};";
            Date queryDate = new Date();
            queryDate.setTime(toDate.getTime()+86400000);
            Query query = new Query(Criteria.where("dateOfService").gte(fromDate).lt(queryDate));

            MapReduceResults<MapReduceValueObjectLocation> res = mt.mapReduce(query, libPrefix + "_task", mapF, reduceF,null, MapReduceValueObjectLocation.class);
            HashMap<String, LocationReport> reportDataSet = new HashMap<String, LocationReport>();
            String locationDescription;
            for (MapReduceValueObjectLocation v : res) {
                Location location = locationRepository.findCoder(libPrefix,v.getId().getLocation());
                LocationReport locationReport = reportDataSet.get(location.getDescription());
                if (location == null){
                    locationDescription= v.getId().getLocation();
                }else{
                    locationDescription = location.getDescription();
                }
                if (locationReport == null) {
                    locationReport = new LocationReport();
                    locationReport.setLocation(locationDescription);
                    reportDataSet.put(locationDescription, locationReport);
                }
                int serviceTypeCode = v.getId().getServiceTypeCode();
                switch (serviceTypeCode) {
                    case 0:
                        locationReport.setFondB(v.getValue().getInLibrary());
                        locationReport.setFondT(v.getValue().getByPhone());
                        locationReport.setFondO(v.getValue().getOnline());
                        break;
                    case 1:
                        locationReport.setUpitB(v.getValue().getInLibrary());
                        locationReport.setUpitT(v.getValue().getByPhone());
                        locationReport.setUpitO(v.getValue().getOnline());
                        break;
                    case 2:
                        locationReport.setKatalogB(v.getValue().getInLibrary());
                        locationReport.setKatalogT(v.getValue().getByPhone());
                        locationReport.setKatalogO(v.getValue().getOnline());
                        break;
                    case 3:
                        locationReport.setProgramB(v.getValue().getInLibrary());
                        locationReport.setProgramT(v.getValue().getByPhone());
                        locationReport.setProgramO(v.getValue().getOnline());
                        break;
                    case 4:
                        locationReport.setServisiB(v.getValue().getInLibrary());
                        locationReport.setServisiT(v.getValue().getByPhone());
                        locationReport.setServisiO(v.getValue().getOnline());
                        break;
                    case 5:
                        locationReport.setBibliotekaB(v.getValue().getInLibrary());
                        locationReport.setBibliotekaT(v.getValue().getByPhone());
                        locationReport.setBibliotekaO(v.getValue().getOnline());
                        break;
                    case 6:
                        locationReport.setOstaloB(v.getValue().getInLibrary());
                        locationReport.setOstaloT(v.getValue().getByPhone());
                        locationReport.setOstaloO(v.getValue().getOnline());
                        break;
                }
            }
            Collection<LocationReport> finalItemList = reportDataSet.values();
            if (finalItemList == null || finalItemList.isEmpty()) {
                response.setStatus(404);
            } else {
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(finalItemList);
                InputStream inputStream = this.getClass().getResourceAsStream("/jaspers/tasksByLocation.jasper");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("fromDate", fromDate);
                params.put("toDate", toDate);
                JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
                inputStream.close();
                response.setContentType("application/octet-stream");
                String attachment = "attachment; filename=acquisitionSheet.pdf";
                response.setHeader("Content-Disposition", attachment);
                OutputStream out = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            }        } catch (Exception e) {
                    e.printStackTrace();
            response.setStatus(500);
        }
    }

    @GetMapping("tasksBySubLocation/{location}")
    public void tasksBySubLocationToPDF(HttpServletResponse response, @PathVariable("location") String location, @RequestParam(name = "fromDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate, @RequestParam(name = "toDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate) throws Exception {
        String libPrefix = lpp.getLibPrefix();

        try {
            String mapF = "function(){\n" +
                    " if (this.method==='Телефоном'){\n" +
                    " emit({\"location\":this.sublocation,\"serviceTypeCode\":this.serviceTypeCode},{\"inLibrary\":0,\"byPhone\":this.quantity, \"online\":0});\n" +
                    " } else if (this.method==='У библиотеци'){\n" +
                    "emit({\"location\":this.sublocation,\"serviceTypeCode\":this.serviceTypeCode},{\"inLibrary\":this.quantity,\"byPhone\":0, \"online\":0});\n" +
                    "}else{\n" +
                    " emit({\"location\":this.sublocation,\"serviceTypeCode\":this.serviceTypeCode},{\"inLibrary\":0,\"byPhone\":0, \"online\":this.quantity});\n" +
                    "}\n" +
                    "}";

            String reduceF = "function(key, values) {\n" +
                    "var res = {\"inLibrary\":0,\"byPhone\":0, \"online\":0}\n" +
                    "values.forEach(v =>{\n" +
                    "res.inLibrary = res.inLibrary + v.inLibrary;\n" +
                    "res.byPhone = res.byPhone + v.byPhone;\n" +
                    "res.online = res.online + v.online;\n" +
                    "});\n" +
                    "return res;\n" +
                    "};";
            Date queryDate = new Date();
            //pomerimo za jedan dan datum kako bi upala i vrednost tog dana u ponoc
            queryDate.setTime(toDate.getTime()+86400000);
            Query query = new Query(Criteria.where("dateOfService").gte(fromDate).lt(queryDate).andOperator(Criteria.where("location").is(location)));

            MapReduceResults<MapReduceValueObjectLocation> res = mt.mapReduce(query, libPrefix + "_task", mapF, reduceF, MapReduceValueObjectLocation.class);
            HashMap<String, LocationReport> reportDataSet = new HashMap<String, LocationReport>();
            String sublocationDescription;
            for (MapReduceValueObjectLocation v : res) {
                CircLocation sublocation = circLocationRepository.findCoder(libPrefix,v.getId().getLocation());
                if (sublocation == null){
                    sublocationDescription= v.getId().getLocation();
                }else{
                    sublocationDescription = sublocation.getDescription();
                }
                LocationReport locationReport = reportDataSet.get(sublocationDescription);
                if (locationReport == null) {
                    locationReport = new LocationReport();
                    locationReport.setLocation(sublocationDescription);
                    reportDataSet.put(sublocationDescription, locationReport);
                }
                int serviceTypeCode = v.getId().getServiceTypeCode();
                switch (serviceTypeCode) {
                    case 0:
                        locationReport.setFondB(v.getValue().getInLibrary());
                        locationReport.setFondT(v.getValue().getByPhone());
                        locationReport.setFondO(v.getValue().getOnline());
                        break;
                    case 1:
                        locationReport.setUpitB(v.getValue().getInLibrary());
                        locationReport.setUpitT(v.getValue().getByPhone());
                        locationReport.setUpitO(v.getValue().getOnline());
                        break;
                    case 2:
                        locationReport.setKatalogB(v.getValue().getInLibrary());
                        locationReport.setKatalogT(v.getValue().getByPhone());
                        locationReport.setKatalogO(v.getValue().getOnline());
                        break;
                    case 3:
                        locationReport.setProgramB(v.getValue().getInLibrary());
                        locationReport.setProgramT(v.getValue().getByPhone());
                        locationReport.setProgramO(v.getValue().getOnline());
                        break;
                    case 4:
                        locationReport.setServisiB(v.getValue().getInLibrary());
                        locationReport.setServisiT(v.getValue().getByPhone());
                        locationReport.setServisiO(v.getValue().getOnline());
                        break;
                    case 5:
                        locationReport.setBibliotekaB(v.getValue().getInLibrary());
                        locationReport.setBibliotekaT(v.getValue().getByPhone());
                        locationReport.setBibliotekaO(v.getValue().getOnline());
                        break;
                    case 6:
                        locationReport.setOstaloB(v.getValue().getInLibrary());
                        locationReport.setOstaloT(v.getValue().getByPhone());
                        locationReport.setOstaloO(v.getValue().getOnline());
                        break;
                }
            }
            Collection<LocationReport> finalItemList = reportDataSet.values();
            if (finalItemList == null || finalItemList.isEmpty()) {
                response.setStatus(404);
            } else {
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(finalItemList);
                InputStream inputStream = this.getClass().getResourceAsStream("/jaspers/tasksByLocation.jasper");
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("fromDate", fromDate);
                params.put("toDate", toDate);
                JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
                inputStream.close();
                response.setContentType("application/octet-stream");
                String attachment = "attachment; filename=acquisitionSheet.pdf";
                response.setHeader("Content-Disposition", attachment);
                OutputStream out = response.getOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, out);
            }
        } catch (Exception e) {
e.printStackTrace();
            response.setStatus(500);
        }
    }
}
