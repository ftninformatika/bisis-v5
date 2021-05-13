package com.ftninformatika.bisis.nabavka.services;

import com.ftninformatika.bisis.nabavka.dto.*;
import com.ftninformatika.bisis.nabavka.model.*;
import com.ftninformatika.bisis.nabavka.repositories.*;
import com.ftninformatika.bisis.core.repositories.LocationRepository;
import com.ftninformatika.utils.LibraryPrefixProvider;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/reports")
public class ReportController {
    @Autowired
    OfferRepository orep;
    @Autowired
    AcquisitionRepository ar;
    @Autowired
    LocationRepository lr;
    @Autowired
    DistributorRepository dr;
    @Autowired
    MongoTemplate mt;
    @Autowired
    LibraryPrefixProvider lpp;
    @Autowired
    DesideratumRepository desr;

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
    }

    @RequestMapping(value = "createDeliverySheet",method = RequestMethod.POST)
    public void exportDeliveryToPDF(HttpServletResponse response, @RequestBody DeliveryReportRequest request) throws Exception{
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(request.getDeliveryList());
        InputStream inputStream = this.getClass().getResourceAsStream("/jaspers/deliverySheet.jasper");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("deliveryTitle", request.getTitle());
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
        inputStream.close();
        response.setContentType("application/octet-stream");
        String attachment = "attachment; filename=deliverySheet.pdf";
        response.setHeader("Content-Disposition", attachment);
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }

    @GetMapping("createAcquisitionSheet/{type}/{id}")
    public void exportAcquisitionToPDF(HttpServletResponse response, @PathVariable String id,@PathVariable String type) throws Exception {
        Acquisition a = ar.findById(id).get();
        JRBeanCollectionDataSource dataSource=null;
        InputStream inputStream=null;
        if (type.equalsIgnoreCase("open")) {
            dataSource = new JRBeanCollectionDataSource(a.getAcquisitionGroupsPlaned());
            inputStream = this.getClass().getResourceAsStream("/jaspers/acquisitionSheet.jasper");

        } else if (type.equalsIgnoreCase("closed")){
            dataSource = new JRBeanCollectionDataSource(a.getAcquisitionGroupsReal());
            inputStream = this.getClass().getResourceAsStream("/jaspers/acquisitionSheet.jasper");

        }else{
            for (AcquisitionGroup ag: a.getAcquisitionGroupsReal()){
                ArrayList cloned = new ArrayList(ag.getItems());

                for(Item i:ag.getItems()){
                    if(i.getAmount()==i.getDesideratum().getAmountDelivered()){
                        cloned.remove(i);
                    }
                }
                ag.setItems(cloned);
            }
            dataSource = new JRBeanCollectionDataSource(a.getAcquisitionGroupsReal());
            inputStream = this.getClass().getResourceAsStream("/jaspers/missingBooks.jasper");
        }
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", a.getTitle());
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
        inputStream.close();
        response.setContentType("application/octet-stream");
        String attachment = "attachment; filename=acquisitionSheet.pdf";
        response.setHeader("Content-Disposition", attachment);
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }


    @GetMapping("createProcruimentSheetXLS/{type}/{id}")
    public void exportProcruimentToXLS(HttpServletResponse response, @PathVariable String id,@PathVariable String type) throws Exception{
        Acquisition a = ar.findById(id).get();
        JRBeanCollectionDataSource dataSource=null;
        if (type.equalsIgnoreCase("open")) {
            dataSource = new JRBeanCollectionDataSource(a.getAcquisitionGroupsPlaned());
        } else{
            dataSource = new JRBeanCollectionDataSource(a.getAcquisitionGroupsReal());
        }
        InputStream inputStream = this.getClass().getResourceAsStream("/jaspers/procruimentSheet.jasper");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", a.getTitle());
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
        inputStream.close();
        response.setContentType("application/octet-stream");
        String attachment = "attachment; filename=acquisitionSheet.xls";
        response.setHeader("Content-Disposition", attachment);
        OutputStream out = response.getOutputStream();
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
    @GetMapping("createOfferSheet/{idOffer}")
    public void exportOffersToPDF(HttpServletResponse response, @PathVariable("idOffer") String idOffer)throws Exception {
        Offer o = orep.findById(idOffer).get();
        Distributor d = dr.findById(o.getDistributor()).get();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(o.getItems());
        InputStream inputStream = this.getClass().getResourceAsStream("/jaspers/offersSheet.jasper");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("library", "Библиотека града Београда");
        params.put("distributor", d.getName());
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
        inputStream.close();
        response.setContentType("application/octet-stream");
        String attachment = "attachment; filename=offerSheet.xls";
        response.setHeader("Content-Disposition", attachment);
        OutputStream out = response.getOutputStream();
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
    @GetMapping("createFinalReport/{year}")
    public void exportFinalReportToPDF(HttpServletResponse response, @PathVariable("year") String year)throws Exception{
       try {
           SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           Date start = sdf.parse(year + "-01-01");
           Date end = sdf.parse(year + "-12-31");
           String library = lpp.getLibPrefix();

           String mapF = "function(){\n" +
                   "    this.acquisitionGroupsReal.forEach(g =>{\n" +
                   "        g.items.forEach(i =>{\n" +
                   "            let loc = i.desideratum.locations;\n" +
                   "            loc.forEach(l => {\n" +
                   "                \n" +
                   "                var finalprice =l.amount *(i.price.price *(1-i.price.rebate/100)*(1+ i.price.vat/100));\n" +
                   "             \n" +
                   "                emit( l.location ,{\"amount\":l.amount,\"price\":finalprice});\n" +
                   "            })\n" +
                   "        })\n" +
                   "        \n" +
                   "    }) \n" +
                   "};";

           String reduceF = "function(key,values){\n" +
                   "    var res = {\"amount\":0, \"price\":0.0}\n" +
                   "    values.forEach(v =>{\n" +
                   "        res.amount = res.amount + v.amount;\n" +
                   "        res.price = res.price + v.price\n" +
                   "    });\n" +
                   "    return res;\n" +
                   "};";
           Query query = new Query(Criteria.where("startDate").gte(start).lte(end));
           MapReduceResults<ValueObject> res = mt.mapReduce(query, library + "_acquisition", mapF, reduceF, ValueObject.class);
           List<ReportItemDTO> finalItemList = new ArrayList<>();
           for (ValueObject v : res) {
               ReportItemDTO r = new ReportItemDTO();
               com.ftninformatika.bisis.coders.Location lc = lr.findCoder(library, v.getId());
               r.setLibrary(lc.getDescription());
               r.setAmount(v.getValue().getAmount());
               r.setPrice(v.getValue().getPrice());
               finalItemList.add(r);
           }
           if (finalItemList.isEmpty()){
               response.setStatus(404);
           }else {
               JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(finalItemList);
               InputStream inputStream = this.getClass().getResourceAsStream("/jaspers/finalReport.jasper");
               Map<String, Object> params = new HashMap<String, Object>();
               params.put("year", year);
               JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
               inputStream.close();
               response.setContentType("application/octet-stream");
               String attachment = "attachment; filename=acquisitionSheet.pdf";
               response.setHeader("Content-Disposition", attachment);
               OutputStream out = response.getOutputStream();
               JasperExportManager.exportReportToPdfStream(jasperPrint, out);
           }
           }catch (Exception e){
             response.setStatus(404);
       }
    }

    @GetMapping("missingBooks/{id}")
    public void exportAcquisitionToPDF(HttpServletResponse response, @PathVariable String id) throws Exception {
        Acquisition a = ar.findById(id).get();
        for (AcquisitionGroup ag: a.getAcquisitionGroupsReal()){
            for(Item i:ag.getItems()){
                if(i.getAmount()==i.getDesideratum().getAmountDelivered()){
                    ag.getItems().remove(i);
                }
            }
        }
        JRBeanCollectionDataSource dataSource=null;
        dataSource = new JRBeanCollectionDataSource(a.getAcquisitionGroupsPlaned());
        InputStream inputStream = this.getClass().getResourceAsStream("/jaspers/acquisitionSheet.jasper");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("title", a.getTitle());
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
        inputStream.close();
        response.setContentType("application/octet-stream");
        String attachment = "attachment; filename=acquisitionSheet.pdf";
        response.setHeader("Content-Disposition", attachment);
        OutputStream out = response.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint,out);
    }

    @GetMapping("createDesiderataSheetXLS")
    public void exportDesiderataToXLS(HttpServletResponse response) throws Exception{
        Sort sort = new Sort(Sort.Direction.DESC, "title");
        List<Desideratum> desideratumList= desr.findAll(sort);
        List<DesideratumReportDTO> reportDTOList = new ArrayList<DesideratumReportDTO>();
        for(Desideratum d:desideratumList){
            DesideratumReportDTO r=new DesideratumReportDTO();
            r.setAuthor(d.getAuthor());
            r.setTitle(d.getTitle());
            if(d.getLocations()!=null){
                for(Location l:d.getLocations()){
                    String loc = l.getLocation();
                    if(loc.equalsIgnoreCase("01")){
                        r.setB01(r.getB01()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("02")){
                        r.setB02(r.getB02()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("03")){
                        r.setB03(r.getB03()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("04")){
                        r.setB04(r.getB04()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("05")){
                        r.setB05(r.getB05()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("06")){
                        r.setB06(r.getB06()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("07")){
                        r.setB07(r.getB07()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("08")){
                        r.setB08(r.getB08()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("09")){
                        r.setB09(r.getB09()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("10")){
                        r.setB10(r.getB10()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("11")){
                        r.setB11(r.getB11()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("12")){
                        r.setB12(r.getB12()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("13")){
                        r.setB13(r.getB13()+l.getAmount());
                    }
                    if(loc.equalsIgnoreCase("15")){
                        r.setB15(r.getB15()+l.getAmount());
                    }
                }

            }
            reportDTOList.add(r);
        }
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportDTOList);
        InputStream inputStream = this.getClass().getResourceAsStream("/jaspers/dezideratiSheet.jasper");
        Map<String, Object> params = new HashMap<String, Object>();
        JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, params, dataSource);
        inputStream.close();
        response.setContentType("application/octet-stream");
        String attachment = "attachment; filename=desiderataSheet.xls";
        response.setHeader("Content-Disposition", attachment);
        OutputStream out = response.getOutputStream();
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }

}

