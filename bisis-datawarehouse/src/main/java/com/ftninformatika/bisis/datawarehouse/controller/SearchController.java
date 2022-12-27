package com.ftninformatika.bisis.datawarehouse.controller;

import com.ftninformatika.bisis.datawarehouse.model.SearchDetailsRequest;
import com.ftninformatika.bisis.datawarehouse.model.SearchRequest;
import com.ftninformatika.bisis.datawarehouse.service.SearchDetailsService;
import com.ftninformatika.bisis.datawarehouse.service.SearchService;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    SearchDetailsService searchDetailsService;

    @PostMapping
    public List<Object[]> search(@RequestBody SearchRequest searchRequest){
        return searchService.search(searchRequest);
    }
    @PostMapping("/details")
    public List<Object[]> searchDetails(@RequestBody SearchDetailsRequest searchDetailsRequestRequest){
        return searchDetailsService.searchDetails(searchDetailsRequestRequest);
    }
    @PostMapping("/details/count")
    public Long countDetails(@RequestBody SearchDetailsRequest searchDetailsRequestRequest){
        return searchDetailsService.countDetails(searchDetailsRequestRequest);
    }
    @PostMapping("/details/download")
    public void downloadDetails(@RequestBody SearchDetailsRequest searchDetailsRequestRequest, HttpServletResponse response){
        try {
            JasperPrint jasperPrint = searchDetailsService.exportDetails(searchDetailsRequestRequest);
            if (jasperPrint != null) {
                response.setContentType("application/octet-stream");
                String attachment = "attachment; filename=detailsReportSheet.xls";
                response.setHeader("Content-Disposition", attachment);
                OutputStream out = response.getOutputStream();
                JRXlsExporter exporter = new JRXlsExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
                SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
                exporter.setConfiguration(configuration);
                exporter.exportReport();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
