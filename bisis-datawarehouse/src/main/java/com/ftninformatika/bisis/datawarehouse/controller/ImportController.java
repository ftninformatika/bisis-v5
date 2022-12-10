package com.ftninformatika.bisis.datawarehouse.controller;

import com.ftninformatika.bisis.datawarehouse.service.ImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/import")
public class ImportController {
    @Autowired
    ImportService importService;

    @GetMapping("/all")
    public void importData(){
        importService.handleImport();
    }

    @GetMapping("/record/{library}")
    public void importRecordData(@PathVariable("library") String library){
        importService.handleImportRecordOneLibrary(library);
    }

    @GetMapping("/coders")
    public void importCoders(){
        importService.importCoders();
    }

    @GetMapping("/member/{library}")
    public void importMemberData(@PathVariable("library") String library){
        importService.handleImportMemberOneLibrary(library);
    }

    @GetMapping("/lending/{library}")
    public void importLendingData(@PathVariable("library") String library){
        importService.handleImportLendingOneLibrary(library);
    }
}
