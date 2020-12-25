package com.ftninformatika.bisis.inventory.controller;

import com.ftninformatika.bisis.inventory.dto.InvUnitSearchDTO;
import com.ftninformatika.bisis.inventory.service.interfaces.InventoryUnitService;
import com.ftninformatika.bisis.rest_service.repository.mongo.LibraryConfigurationRepository;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reports")
public class InventoryReportController {

    private InventoryUnitService inventoryUnitService;
    private LibraryConfigurationRepository libraryConfigurationRepository;
   @Autowired
    public InventoryReportController(InventoryUnitService inventoryUnitService, LibraryConfigurationRepository libraryConfigurationRepository){
        this.inventoryUnitService = inventoryUnitService;
        this.libraryConfigurationRepository = libraryConfigurationRepository;
    }
    @PostMapping("/generate")
    public void exportToXLS(@RequestHeader ("Library") String library, HttpServletResponse response, @RequestBody InvUnitSearchDTO invUnitSearchDTO) throws Exception{
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(inventoryUnitService.search(invUnitSearchDTO));
        JasperCompileManager.compileReportToFile(this.getClass().getResource("/jaspers/inventoryReportSheet.jrxml").getPath(), this.getClass().getResource("/jaspers/inventoryReportSheet.jasper").getPath());
        InputStream inputStream = InventoryReportController.class.getResourceAsStream("/jaspers/inventoryReportSheet.jasper");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("query", invUnitSearchDTO.toString());
        String libraryFullName = libraryConfigurationRepository.getByLibraryName(library).getLibraryFullName();
        params.put("library", libraryFullName);
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
}
